package net.rdrei.savehtml.extractor

import com.helger.commons.charset.CCharset
import com.helger.css.ECSSVersion
import com.helger.css.reader.CSSReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.net.URI

public class CssExtractorTest {
    @Test
    public fun testUnidirectionalImportExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css0/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css)

        val expected = TestUtil.openListResource("net/rdrei/savehtml/extractor/css0/imports.txt")
            .map { URI(it) }

        assertThat(resources.styles).hasSameElementsAs(expected)
        assertThat(resources.resources).isEmpty()
    }

    @Test
    public fun testUnidirectionalExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css1/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css)

        val expected = TestUtil.openListResource("net/rdrei/savehtml/extractor/css1/resources.txt")
                .map { URI(it) }

        assertThat(resources.styles).isEmpty()
        assertThat(resources.resources).hasSameElementsAs(expected)
    }
}

