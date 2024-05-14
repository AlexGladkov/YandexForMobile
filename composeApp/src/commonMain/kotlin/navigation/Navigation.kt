package navigation

import App
import browser.browserFlow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {
    screen(name = MainNavigation.startScreen) {
        App()
    }
    
    sampleFlow()
    browserFlow()
}

object MainNavigation {
    const val startScreen = "StartScreen"
}