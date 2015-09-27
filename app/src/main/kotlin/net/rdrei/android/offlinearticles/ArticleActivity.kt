package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.webkit.*
import com.trello.rxlifecycle.components.RxActivity
import org.jetbrains.anko.*
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

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
        val requests = ConcurrentLinkedDeque<WebResourceRequest>()

        wv.setWebViewClient(object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                if (request != null) {
                    info("Yolo: " + request.url)
                    requests.add(request)
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                info { "Site Loaded. Requests: " }
                requests.forEach {
                    info(it.method + " " + it.url)
                    val hashCode = java.lang.String.format("%08x", it.url.hashCode())
                    val f = File(File(getCacheDir(), "webviewCache"), hashCode)
                    info("File: " + f)
                }
                super.onPageFinished(view, url)
            }
        })

        wv.loadUrl("https://twitter.com/passy")
    }
}

