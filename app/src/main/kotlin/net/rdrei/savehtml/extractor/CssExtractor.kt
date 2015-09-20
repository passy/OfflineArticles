package net.rdrei.savehtml.extractor

import com.osbcp.cssparser.CSSParser
import com.osbcp.cssparser.Rule
import java.util.*

data class StyleSheet(val data: String)

/**
 * A non-recursive extractor for CSS resources.
 */
public object CssExtractor {
    public fun extract(document: StyleSheet): Resources {
        val rules: List<Rule> = CSSParser.parse(document.data)

        return Resources(Collections.emptySet(), Collections.emptySet())
    }
}