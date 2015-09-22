package net.rdrei.savehtml.extractor

import com.helger.commons.charset.CCharset
import com.helger.css.ECSSVersion
import com.helger.css.reader.CSSReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

public class CssExtractorTest {
    @Test
    public fun testUnidirectionalImportExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css0/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css)

        assertThat(resources.styles).hasSize(14)
        assertThat(resources.resources).hasSize(0)
    }

    @Test
    public fun testUnidirectionalExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css1/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css)

        assertThat(resources.styles).hasSize(0)
        // TODO
        assertThat(resources.resources).hasSize(0)
    }
}

