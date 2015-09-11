package net.rdrei.savehtml.extractor

import org.jsoup.nodes.Document
import java.net.URI
import java.util.*


data class HtmlResources(val resources: List<URI>)

public object HtmlExtractor {
    fun extract(doc: Document): HtmlResources {
        val media: List<String> = doc.select("[src]").map { it.attr("abs:src") }
        val imports: List<String> = doc.select("link[href]").map { it.attr("abs:href") }

        val uris: List<URI> = media.plus(imports).map { URI(it) }.distinct()
        return HtmlResources(
                resources = Collections.unmodifiableList(uris)
        )
    }
}


