package net.rdrei.savehtml.extractor

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import java.io.InputStream

import org.assertj.core.api.Assertions.*


public class HtmlExtractorTest {
    val BASE_PATH = "https://base/"

    Test
    public fun extractGithubPage() {
        val results = HtmlExtractor.extract(openDocumentResource("net/rdrei/savehtml/extractor/sample0.html"))
        println(results.resources)
        assertThat(results.resources).hasSize(42)
    }

    private fun openResource(filename: String): InputStream {
        return javaClass.getClassLoader().getResourceAsStream(filename)
    }

    private fun openDocumentResource(filename: String): Document {
        return Jsoup.parse(openResource(filename), "utf-8", BASE_PATH)
    }
}
