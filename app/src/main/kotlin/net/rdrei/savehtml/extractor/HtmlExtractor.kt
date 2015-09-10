package net.rdrei.savehtml.extractor

import android.net.Uri
import org.jsoup.nodes.Document
import java.util.*


data class HtmlResources(
        val resources: Map<Uri, Uri>
)

public object HtmlExtractor {
    fun extract(doc: Document): HtmlResources {
        return HtmlResources(HashMap())
    }
}


