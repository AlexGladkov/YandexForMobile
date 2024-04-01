package navigation

import detail.SampleDetailScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import sample.SampleScreen

fun RootComposeBuilder.sampleFlow() {
    flow(name = SampleNavigation.sampleFlow) {
        screen(name = SampleNavigation.sampleScreen) {
            SampleScreen()
        }
        
        screen(name = SampleNavigation.sampleDetailScreen) {
            SampleDetailScreen()
        }
    }
}

object SampleNavigation {
    const val sampleFlow = "SampleFlow"
    const val sampleScreen = "SampleScreen"
    const val sampleDetailScreen = "SampleDetailScreen"
}

