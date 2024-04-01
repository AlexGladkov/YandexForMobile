package tech.mobiledeveloper.worlds

import App
import PlatformConfiguration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import di.PlatformSDK
import io.github.alexgladkov.kviewmodel.odyssey.setupWithViewModels
import navigation.MainNavigation
import navigation.navigationGraph
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootController = RootComposeBuilder().apply { navigationGraph() }.build()
        rootController.setupWithActivity(this)
        rootController.setupWithViewModels()

        PlatformSDK.init(PlatformConfiguration(applicationContext))

        setContent {
            val backgroundColor = MaterialTheme.colors.background
            rootController.backgroundColor = backgroundColor

            CompositionLocalProvider(
                LocalRootController provides rootController
            ) {
                ModalNavigator(configuration = DefaultModalConfiguration(backgroundColor, DisplayType.EdgeToEdge)) {
                    Navigator(startScreen = MainNavigation.startScreen)
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}