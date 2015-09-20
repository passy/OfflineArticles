package net.rdrei.savehtml.extractor

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import java.io.File
import java.io.InputStream
import java.net.URI


public class HtmlExtractorTest {
    val BASE_PATH = "https://base/"

    @Test
    public fun extractGithubPage() {
        val results =
                HtmlExtractor.extract(openDocumentResource("net/rdrei/savehtml/extractor/html0/input.html"))
        val expectedResources =
                TestUtil.openListResource("net/rdrei/savehtml/extractor/html0/resources.txt").map { URI(it) }
        val expectedStyles =
                TestUtil.openListResource("net/rdrei/savehtml/extractor/html0/styles.txt").map { URI(it) }
        assertThat(results.resources).hasSameElementsAs(expectedResources)
        assertThat(results.styles).hasSameElementsAs(expectedStyles)
    }

    private fun openDocumentResource(filename: String): Document {
        return Jsoup.parse(TestUtil.openResource(filename), "utf-8", BASE_PATH)
    }
}
