package net.rdrei.android.offlinearticles.model

import com.parse.ParseClassName
import com.parse.ParseObject
import java.net.URI

ParseClassName("Bookmark")
class Bookmark : ParseObject {
    val KEY_URI = "URI"

    constructor() : super() {
    }

    constructor(uri: URI) : super() {
        setURI(uri)
    }

    public fun setURI(uri: URI) {
        put(KEY_URI, uri.toString())
    }

    public fun getURI(default: URI): URI {
        val str = get(KEY_URI) as String?
        return if (str != null) URI(str) else default
    }
}