package net.rdrei.savehtml.extractor

import java.io.File
import java.io.InputStream

public object TestUtil {
    internal fun openResource(filename: String): InputStream {
        return javaClass.classLoader.getResourceAsStream(filename)
    }

    internal fun openListResource(filename: String): List<String> {
        return File(javaClass.classLoader.getResource(filename).toURI()).readLines()
    }
}
