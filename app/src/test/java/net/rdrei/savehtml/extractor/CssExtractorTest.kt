package net.rdrei.savehtml.extractor

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

public class CssExtractorTest {
    @Test
    public fun testUnidirectionalExtraction() {
        val input = StyleSheet(TestUtil.openStringResource("net/rdrei/savehtml/extractor/css0/styles.css"))
        val resources = CssExtractor.extract(input)

        // TODO
        assertThat(resources.styles).hasSize(0)
        assertThat(resources.resources).hasSize(0)
    }
}

