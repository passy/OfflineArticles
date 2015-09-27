package net.rdrei.savehtml.extractor

import android.net.Uri
import com.helger.css.decl.*
import com.helger.css.decl.visit.CSSVisitor
import com.helger.css.decl.visit.DefaultCSSUrlVisitor
import java.io.File
import java.net.URI
import java.net.URL
import java.util.*

/**
 * A non-recursive extractor for CSS resources.
 */
public object CssExtractor {
    public fun extract(document: CascadingStyleSheet, baseUri: Uri): Resources {
        val importRules: List<CSSImportRule> = document.allImportRules ?: Collections.emptyList()
        val importUris = importRules.map { Uri.parse(it.location.uri) }

        // Boo, no way to do this immutably. :(
        val urlDecls = ArrayList<Uri>()
        CSSVisitor.visitCSSUrl(document, object : DefaultCSSUrlVisitor() {
            override fun onUrlDeclaration(aTopLevelRule: ICSSTopLevelRule?,
                                          aDeclaration: CSSDeclaration?,
                                          aURITerm: CSSExpressionMemberTermURI?) {
                if (aURITerm != null && !aURITerm.uri.isDataURL) {
                    urlDecls.add(Uri.parse(aURITerm.uriString))
                }
            }
        })

        return Resources(
                Collections.unmodifiableSet(urlDecls.map { absolutizeURI(baseUri, it) }.toSet()),
                Collections.unmodifiableSet(importUris.map { absolutizeURI(baseUri, it) }.toSet()))
    }

    fun absolutizeURI(baseURI: Uri, uri: Uri): Uri =
        if (uri.isAbsolute) {
            uri
        } else {
            baseURI.buildUpon()
                .encodedPath(if (uri.path.first() == '/') {
                    uri.encodedPath
                } else {
                    File(baseURI.encodedPath.toString(), uri.encodedPath.toString())
                            .normalize().toString()
                })
                .encodedFragment(uri.encodedFragment)
                .encodedQuery(uri.encodedQuery)
                .build()
        }
}
