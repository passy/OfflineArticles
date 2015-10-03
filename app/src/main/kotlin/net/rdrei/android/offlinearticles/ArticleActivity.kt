package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.trello.rxlifecycle.components.RxActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.webView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.observable
import java.net.URL

fun String.toURL() = URL(this)

object WebViewResourceExtractor {
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
        WebViewResourceExtractor
            .extract(wv, "https://github.com/passy".toURL())
            .subscribe { r ->
                info("Next: " + r.url)
            }
    }
}

