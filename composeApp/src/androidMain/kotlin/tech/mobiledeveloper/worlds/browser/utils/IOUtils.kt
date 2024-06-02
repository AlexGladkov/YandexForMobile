package tech.mobiledeveloper.worlds.browser.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object IOUtils {

    private const val BUFFER_SIZE = 2048

    @Throws(IOException::class)
    fun read(inputStream: InputStream): String {
        val buffer = CharArray(BUFFER_SIZE)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val builder = StringBuilder(inputStream.available())
        var read: Int
        while (reader.read(buffer).also { read = it } != -1) {
            builder.appendRange(buffer, 0, read)
        }
        return builder.toString()
    }
}
