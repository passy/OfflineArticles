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

    Test
    public fun extractGithubPage() {
        val results = HtmlExtractor.extract(openDocumentResource("net/rdrei/savehtml/extractor/sample0.html"))
        val expected = openListResource("net/rdrei/savehtml/extractor/sample0-expected.txt").map { URI(it) }
        assertThat(results.resources).hasSameElementsAs(expected)
    }

    private fun openResource(filename: String): InputStream {
        return javaClass.getClassLoader().getResourceAsStream(filename)
    }

    private fun openListResource(filename: String): List<String> {
        return File(javaClass.getClassLoader().getResource(filename).toURI()).readLines()
    }

    private fun openDocumentResource(filename: String): Document {
        return Jsoup.parse(openResource(filename), "utf-8", BASE_PATH)
    }
}
