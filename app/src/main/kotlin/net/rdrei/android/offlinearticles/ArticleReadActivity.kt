package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.trello.rxlifecycle.components.RxActivity
import net.rdrei.android.offlinearticles.util.Crypto
import okio.ByteString
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.webView
import java.io.File
import java.io.FileInputStream
import java.net.URL

public class ArticleReadActivity : RxActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)

        var articleWebView: WebView? = null
        verticalLayout {
            articleWebView = webView() {
                id = R.id.articleWebView

            }
        }

        val url = "https://github.com/passy".toURL()
        if (articleWebView != null) {
            setupWebView(articleWebView as WebView, url)
        }
    }

    private fun setupWebView(wv: WebView, baseURL: URL): Unit {
        val urlSha: String = Crypto.sha256(ByteString.encodeUtf8(baseURL.toString())).hex()
        val basePath = File(File(filesDir, "changemeplase"), urlSha)

        wv.setWebViewClient(object: WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                if (request != null) {
                    info("Requesting: " + request.url)
                    val sha = hashRequest(request)
                    val file = File(basePath, sha)

                    if (file.exists()) {
                        val input = FileInputStream(file)
                        // TODO: Hm, looks like I need to save this ...
                        info("Found file: " + file.absolutePath)
                        return WebResourceResponse("text/html", "utf-8", input)
                    }
                    info("Couldn't find file, not intercepting." + file.absolutePath)
                }
                return super.shouldInterceptRequest(view, request)
            }
        })

        wv.loadUrl(baseURL.toString())
    }

    // FIXME: Copy from SaveActivity. Should obviously get pulled out and
    // FIXME: maybe also take (some) headers into account.
    private fun hashRequest(req: WebResourceRequest): String =
            Crypto.sha256(
                    ByteString.encodeUtf8(req.url.toString())
            ).hex()
}
