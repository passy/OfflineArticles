package net.rdrei.savehtml.extractor

import android.net.Uri
import org.jsoup.nodes.Document
import java.util.*


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
    fun extract(doc: Document): Resources {
        val src = doc.select("[src]")
                .map { it.attr("abs:src") }
                .map { Uri.parse(it) }
        val href = doc.select("link[href]")
                .filter {
                    val rel = it.attr("rel")
                    rel == null || rel != "stylesheet"
                }
                .map { it.attr("abs:href") }
                .map { Uri.parse(it) }
        val resources: Set<Uri> = src.plus(href).toSet()

        val stylesheets: Set<Uri> = doc.select("link[href][rel=\"stylesheet\"]")
                .map { it.attr("abs:href") }
                .map { Uri.parse(it) }
                .toSet()

        return Resources(
                resources = Collections.unmodifiableSet(resources),
                styles = Collections.unmodifiableSet(stylesheets)
        )
    }
}


