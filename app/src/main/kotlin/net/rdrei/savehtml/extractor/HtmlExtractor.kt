package net.rdrei.savehtml.extractor

import org.jsoup.nodes.Document
import java.net.URI
import java.util.*


data class HtmlResources(val resources: Set<URI>, val styles: Set<URI>)

/**
 * TODO:
 *
 * - Base element? Is this handled by jsoup?
 * - Picture element? Can I rely on the fallback?
 * - Iframes? GitHub gists for example?
 */

/**
 * Extract external resources from an HTML page, non-recursively. Covers
 * stylesheets, link resources and elements specifying an `src` attribute.
 */
public object HtmlExtractor {
    fun extract(doc: Document): HtmlResources {
        val src = doc.select("[src]")
                .map { it.attr("abs:src") }
                .map { URI(it) }
        val href = doc.select("link[href]")
                .filter {
                    val rel = it.attr("rel")
                    rel == null || rel != "stylesheet"
                }
                .map { it.attr("abs:href") }
                .map { URI(it) }
        val resources: Set<URI> = src.plus(href).toSet()

        val stylesheets: Set<URI> = doc.select("link[href][rel=\"stylesheet\"]")
                .map { it.attr("abs:href") }
                .map { URI(it) }
                .toSet()

        return HtmlResources(
                resources = Collections.unmodifiableSet(resources),
                styles = Collections.unmodifiableSet(stylesheets)
        )
    }
}


