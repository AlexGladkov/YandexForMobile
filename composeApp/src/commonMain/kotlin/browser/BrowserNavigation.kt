package browser

import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.browserFlow() {
    flow(name = BrowserNavigation.BROWSER_FLOW) {
        screen(name = BrowserNavigation.BROWSER_SCREEN) {
            BrowserScreen()
        }
    }
}

object BrowserNavigation {
    const val BROWSER_FLOW = "BrowserFlow"
    internal const val BROWSER_SCREEN = "BrowserScreen"
}
