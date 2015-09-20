package net.rdrei.savehtml.extractor

import com.steadystate.css.dom.CSSStyleSheetImpl
import com.steadystate.css.parser.CSSOMParser
import com.steadystate.css.parser.SACParserCSS3
import org.junit.Test
import org.w3c.css.sac.InputSource
import java.io.InputStreamReader
import java.io.StringReader
import org.w3c.dom.css.CSSStyleSheet

public class CssExtractorTest {
    @Test
    public fun testUnidirectionalExtraction() {
        val input = openStyleSheetResource("net/rdrei/savehtml/extractor/css0/styles.css")

        CssExtractor.extract(Any())
    }

    private fun openStyleSheetResource(filename: String): CSSStyleSheet {
        val parser = CSSOMParser(SACParserCSS3())
        val reader = InputStreamReader(TestUtil.openResource(filename))
        val input = InputSource(reader)
        return parser.parseStyleSheet(input, null, null)
    }
}
