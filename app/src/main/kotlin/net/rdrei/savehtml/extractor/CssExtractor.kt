package net.rdrei.savehtml.extractor

import com.helger.css.decl.CSSImportRule
import com.helger.css.decl.CascadingStyleSheet
import java.net.URI
import java.util.*

/**
 * A non-recursive extractor for CSS resources.
 */
public object CssExtractor {
    public fun extract(document: CascadingStyleSheet): Resources {
        val importRules: List<CSSImportRule> = document.allImportRules ?: Collections.emptyList()
        val importUris = importRules.map { URI(it.location.uri) }

        return Resources(
                Collections.emptySet(),
                Collections.unmodifiableSet(importUris.toSet()))
    }
}
