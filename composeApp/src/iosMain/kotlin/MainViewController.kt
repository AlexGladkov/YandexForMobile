import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import di.PlatformSDK
import io.github.alexgladkov.kviewmodel.odyssey.setupWithViewModels
import navigation.MainNavigation
import navigation.navigationGraph
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType

fun MainViewController(openFlutterPro: () -> Unit,
                       openBrowserDivKit: () -> Unit) = ComposeUIViewController {
    val rootController = remember { RootComposeBuilder().apply { navigationGraph() }.build() }
    rootController.setupWithViewModels()

    PlatformSDK.init(PlatformConfiguration(openFlutterPro, openBrowserDivKit))
    
    val backgroundColor = MaterialTheme.colors.background
    rootController.backgroundColor = backgroundColor

    CompositionLocalProvider(
        LocalRootController provides rootController
    ) {
        ModalNavigator(configuration = DefaultModalConfiguration(backgroundColor, displayType = DisplayType.EdgeToEdge)) {
            Navigator(startScreen = MainNavigation.startScreen)
        }
    }
}