package maps

import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.mapsFlow() {
    flow(name = MapsNavigation.FLOW) {
        screen(name = MapsNavigation.SCREEN) {
            MapsPresentationScreen()
        }

        screen(name = MapsNavigation.HAVE_FUN_WITH_MAP) {
            MapFunScreen()
        }
    }
}

object MapsNavigation {
    const val FLOW = "MapsFlow"
    internal const val SCREEN = "MapsScreen"
    internal const val HAVE_FUN_WITH_MAP = "HaveFunWithMap"
}
