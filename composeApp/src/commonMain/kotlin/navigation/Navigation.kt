package navigation

import App
import browser.browserFlow
import maps.mapsFlow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {
    screen(name = MainNavigation.startScreen) {
        App()
    }

    sampleFlow()
    browserFlow()
    mapsFlow()
}

object MainNavigation {
    const val startScreen = "StartScreen"
}