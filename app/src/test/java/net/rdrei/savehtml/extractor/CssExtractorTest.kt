package net.rdrei.savehtml.extractor

import android.net.Uri
import com.helger.commons.charset.CCharset
import com.helger.css.ECSSVersion
import com.helger.css.reader.CSSReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
public class CssExtractorTest {
    val BASE_URI = Uri.parse("https://example.com/page/")

    @Test
    public fun testUnidirectionalImportExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css0/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css, BASE_URI)

        val expected = TestUtil.openListResource("net/rdrei/savehtml/extractor/css0/imports.txt")
            .map { Uri.parse(it) }

        assertThat(resources.styles).hasSameElementsAs(expected)
        assertThat(resources.resources).isEmpty()
    }

    @Test
    public fun testUnidirectionalExtraction() {
        val load = { -> TestUtil.openResource("net/rdrei/savehtml/extractor/css1/styles.css") }
        val css = CSSReader.readFromStream(load, CCharset.CHARSET_UTF_8_OBJ, ECSSVersion.LATEST)
        val resources = CssExtractor.extract(css, BASE_URI)

        val expected = TestUtil.openListResource("net/rdrei/savehtml/extractor/css1/resources.txt")
                .map { Uri.parse(it) }

        assertThat(resources.styles).isEmpty()
        assertThat(resources.resources).hasSameElementsAs(expected)
    }
}

