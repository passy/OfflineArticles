package net.rdrei.android.offlinearticles

import android.os.Bundle
import android.webkit.WebView
import com.trello.rxlifecycle.components.RxActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.id
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.webView

public class ArticleActivity : RxActivity(), AnkoLogger {
    var articleWebView: WebView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super<RxActivity>.onCreate(savedInstanceState)

        verticalLayout {
            articleWebView = webView() {
                id = R.id.articleWebView;

            }
        }

        articleWebView?.loadUrl("https://example.com/")
    }
}

