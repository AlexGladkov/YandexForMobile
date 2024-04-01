package navigation

import detail.SampleDetailScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import sample.SampleScreen

fun RootComposeBuilder.sampleFlow() {
    flow(name = SampleNavigation.sampleFlow) {
        screen(name = SampleNavigation.sampleScreen) {
            SampleScreen(it as String)
        }
        
        screen(name = SampleNavigation.sampleDetailScreen) {
            SampleDetailScreen()
        }
    }
}

object SampleNavigation {
    const val sampleFlow = "SampleFlow"
    internal const val sampleScreen = "SampleScreen"
    internal const val sampleDetailScreen = "SampleDetailScreen"
}

