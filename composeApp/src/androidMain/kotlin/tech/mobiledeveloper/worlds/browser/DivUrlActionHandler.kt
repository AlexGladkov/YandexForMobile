package tech.mobiledeveloper.worlds.browser

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class DivUrlActionHandler(private val context: Context) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)
        val uri = Uri.parse(url.toString())
        if ("http".equals(uri.scheme, ignoreCase = true)
            || "https".equals(uri.scheme, ignoreCase = true)
        ) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(uri)
            context.startActivity(intent)
            return true
        }
        return super.handleAction(action, view, resolver)
    }
}