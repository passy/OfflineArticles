package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.trello.rxlifecycle.components.RxActivity
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
import java.io.IOError
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors

fun String.toURL() = URL(this)

object WebViewResourceExtractor {
    val client: OkHttpClient = OkHttpClient()
    // TODO: Inject, reuse an Application-wide one, pls.

    public fun extract(wv: WebView, url: URL): Observable<WebResourceRequest> =
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

            wv.loadUrl(url.toString())
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
}

public class ArticleActivity : RxActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val scheduler = Schedulers.from(executor)
        WebViewResourceExtractor
            .extract(wv, "https://github.com/passy".toURL())
            .observeOn(scheduler)
            .doOnEach { uri -> info("onEach: " + uri) }
            .flatMap { WebViewResourceExtractor.downloadRequest(it) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { r: Response ->
                info("Next: " + r.headers())
            }
    }
}

