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
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
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
        warmUpEngine()
        PlatformSDK.init(PlatformConfiguration {
            startActivity(
                FlutterActivity.CachedEngineIntentBuilder(
                    FlutterModuleActivity::class.java,
                    "flutter_engine"
                ).build(this)
            )
        })

        setContent {
            val backgroundColor = MaterialTheme.colors.background
            rootController.backgroundColor = backgroundColor

            CompositionLocalProvider(
                LocalRootController provides rootController
            ) {
                ModalNavigator(
                    configuration = DefaultModalConfiguration(
                        backgroundColor,
                        DisplayType.EdgeToEdge
                    )
                ) {
                    Navigator(startScreen = MainNavigation.startScreen)
                }
            }
        }
    }

    private fun warmUpEngine() {
        // Instantiate a FlutterEngine.
        val flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("flutter_engine", flutterEngine)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}