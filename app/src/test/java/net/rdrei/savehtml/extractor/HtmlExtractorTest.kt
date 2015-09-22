package net.rdrei.savehtml.extractor

import android.net.Uri
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
public class HtmlExtractorTest {
    val BASE_PATH = "https://base/"

    @Test
    public fun extractGithubPage() {
        val results =
                HtmlExtractor.extract(openDocumentResource("net/rdrei/savehtml/extractor/html0/input.html"))
        val expectedResources =
                TestUtil.openListResource("net/rdrei/savehtml/extractor/html0/resources.txt").map { Uri.parse(it) }
        val expectedStyles =
                TestUtil.openListResource("net/rdrei/savehtml/extractor/html0/styles.txt").map { Uri.parse(it) }
        assertThat(results.resources).hasSameElementsAs(expectedResources)
        assertThat(results.styles).hasSameElementsAs(expectedStyles)
    }

    private fun openDocumentResource(filename: String): Document {
        return Jsoup.parse(TestUtil.openResource(filename), "utf-8", BASE_PATH)
    }
}
