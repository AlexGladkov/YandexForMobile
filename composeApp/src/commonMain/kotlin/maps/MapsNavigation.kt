package maps

import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.mapsFlow() {
    flow(name = MapsNavigation.FLOW) {
        screen(name = MapsNavigation.SCREEN) {
            MapsScreen()
        }
    }
}

object MapsNavigation {
    const val FLOW = "MapsFlow"
    internal const val SCREEN = "MapsScreen"
}
