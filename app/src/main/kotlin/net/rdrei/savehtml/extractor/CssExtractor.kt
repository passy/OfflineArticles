package net.rdrei.savehtml.extractor

import com.helger.css.decl.*
import com.helger.css.decl.visit.CSSVisitor
import com.helger.css.decl.visit.CSSVisitorForUrl
import com.helger.css.decl.visit.DefaultCSSUrlVisitor
import java.net.URI
import java.util.*

/**
 * A non-recursive extractor for CSS resources.
 */
public object CssExtractor {
    public fun extract(document: CascadingStyleSheet): Resources {
        val importRules: List<CSSImportRule> = document.allImportRules ?: Collections.emptyList()
        val importUris = importRules.map { URI(it.location.uri) }

        // Boo, no way to do this immutably. :(
        val urlDecls = ArrayList<URI>()
        CSSVisitor.visitCSSUrl(document, object : DefaultCSSUrlVisitor() {
            override fun onUrlDeclaration(aTopLevelRule: ICSSTopLevelRule?,
                                          aDeclaration: CSSDeclaration?,
                                          aURITerm: CSSExpressionMemberTermURI?) {
                if (aURITerm != null && !aURITerm.uri.isDataURL) {
                    urlDecls.add(URI(aURITerm.uriString))
                }
            }
        })

        return Resources(
                Collections.unmodifiableSet(urlDecls.toSet()),
                Collections.unmodifiableSet(importUris.toSet()))
    }
}
