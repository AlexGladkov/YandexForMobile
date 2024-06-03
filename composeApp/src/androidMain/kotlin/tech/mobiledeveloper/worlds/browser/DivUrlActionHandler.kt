package tech.mobiledeveloper.worlds.browser

import android.content.Context
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction
import tech.mobiledeveloper.worlds.browser.utils.UrlUtils

class DivUrlActionHandler(private val context: Context) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver,
    ): Boolean {
        val uri = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)
        if (UrlUtils.isHttpOrHttps(uri) && UrlUtils.openUrlExternally(context, uri)) {
            return true
        }
        return super.handleAction(action, view, resolver)
    }
}