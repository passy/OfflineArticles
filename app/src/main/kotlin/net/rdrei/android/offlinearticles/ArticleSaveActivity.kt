package net.rdrei.android.offlinearticles

import android.content.Context
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.internal.Util
import com.trello.rxlifecycle.components.RxActivity
import net.rdrei.android.offlinearticles.util.Crypto
import okio.ByteString
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.webView
import retrofit.Retrofit
import rx.Notification
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import rx.plugins.RxJavaPlugins
import rx.schedulers.Schedulers
import java.io.File
import java.io.IOError
import java.io.IOException
import java.net.URL
import java.security.MessageDigest
import java.util.concurrent.Executors

fun String.toURL() = URL(this)

class WebViewResourceExtractor(val baseURL: URL) {
    val client: OkHttpClient = OkHttpClient()
    val urlSha: String = Crypto.sha256(ByteString.encodeUtf8(baseURL.toString())).hex()

    // TODO: Inject, reuse an Application-wide one, pls.
    public fun extract(wv: WebView): Observable<WebResourceRequest> =
        observable { subscriber ->
            wv.setWebViewClient(object : WebViewClient() {
                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                    if (request != null && !subscriber.isUnsubscribed) {
                        subscriber.onNext(request)
                    }
                    return super.shouldInterceptRequest(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    subscriber.onCompleted()
                    super.onPageFinished(view, url)
                }
            })

            wv.loadUrl(baseURL.toString())
        }

    public fun downloadRequest(req: WebResourceRequest): Observable<Response> =
        observable { sub ->
            val httpReq = Request.Builder()
                    .url(req.url.toString())
                    .build()

            val resp = client
                .newCall(httpReq)
                .execute()

            if (resp.isSuccessful) {
                sub.onNext(resp)
                sub.onCompleted()
            } else {
                sub.onError(IOException(resp.message()))
            }
        }

    fun saveResponse(response: Response, context: Context): Unit {
        // TODO: Get filesDir thing from an injected configuration object
        val basePath = File(File(context.filesDir, "changemeplase"), urlSha)
        // TODO: Handle errors, sigh ... Could already exist which is fine, but also fail writing.
        basePath.mkdirs()
        File(basePath, hashResponse(response)).writeBytes(response.body().bytes())
    }

    fun hashResponse(response: Response): String =
        Crypto.sha256(
                ByteString.encodeUtf8(response.request().url().toString())
        ).hex()
}

public class ArticleSaveActivity : RxActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)

        var articleWebView: WebView? = null
        verticalLayout {
            articleWebView = webView() {
                id = R.id.articleWebView

            }
        }

        if (articleWebView != null) {
            setupWebView(articleWebView as WebView)
        }
    }

    fun setupWebView(wv: WebView): Unit {
        // TODO: Consider using Schedulers.io()
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val scheduler = Schedulers.from(executor)
        val url = "https://github.com/passy".toURL()
        val extractor = WebViewResourceExtractor(url)

        extractor
            .extract(wv)
            .observeOn(scheduler)
            .doOnEach { info("onEach: " + it) }
            .flatMap { extractor.downloadRequest(it) }
            .doOnNext { extractor.saveResponse(it, applicationContext) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { r: Response ->
                info("Next: " + r.request().urlString())
            }
    }
}

