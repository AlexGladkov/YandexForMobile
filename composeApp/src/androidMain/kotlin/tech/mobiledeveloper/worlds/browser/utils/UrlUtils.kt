package tech.mobiledeveloper.worlds.browser.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

object UrlUtils {

    private const val SCHEME_HTTP = "http"
    private const val SCHEME_HTTPS = "https"

    fun isHttpOrHttps(uri: Uri): Boolean {
        return SCHEME_HTTP.equals(uri.scheme, ignoreCase = true)
                || SCHEME_HTTPS.equals(uri.scheme, ignoreCase = true)
    }

    fun openUrlExternally(context: Context, uri: Uri): Boolean {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(uri)
        return try {
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }
}
