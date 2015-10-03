package net.rdrei.android.offlinearticles.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import okio.ByteString

object Crypto {
    /** Returns a Base 64-encoded string containing a SHA-1 hash of `s`. */
    fun shaBase64(s: ByteString): String =
        ByteString.of(*sha256Bytes(s)).base64()

    /** Returns a SHA-256 hash of `s`. */
    fun sha256(s: ByteString): ByteString =
        ByteString.of(*sha256Bytes(s))

    private fun sha256Bytes(s: ByteString): ByteArray {
        try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            return messageDigest.digest(s.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        }
    }
}
