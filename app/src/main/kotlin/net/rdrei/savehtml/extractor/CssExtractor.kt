package net.rdrei.savehtml.extractor

import com.helger.css.decl.CascadingStyleSheet
import java.util.*

/**
 * A non-recursive extractor for CSS resources.
 */
public object CssExtractor {
    public fun extract(document: CascadingStyleSheet): Resources {
        return Resources(Collections.emptySet(), Collections.emptySet())
    }
}