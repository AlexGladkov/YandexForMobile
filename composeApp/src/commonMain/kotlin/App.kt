import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import browser.BrowserNavigation
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import core.getAsyncImageLoader
import di.Inject
import models.Module
import models.ModuleTech
import navigation.SampleNavigation
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kodein.di.instance
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.future_app_logo
import worlds.composeapp.generated.resources.pro_logo
import worlds.composeapp.generated.resources.sample_app_logo
import worlds.composeapp.generated.resources.secret_app_logo
import worlds.composeapp.generated.resources.unknown_app_logo
import worlds.composeapp.generated.resources.yabro_logo
import kotlin.random.Random

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    val rootController = LocalRootController.current
    val platformConfiguration by derivedStateOf { Inject.di.instance<PlatformConfiguration>() }
    val worlds = remember { Apps.apps }

    Column {
        Spacer(modifier = Modifier.height(72.dp))

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Я!ВСЕ", fontSize = 32.sp, fontWeight = FontWeight.Bold,
            fontFamily = YSFontFamily()
        )

        LazyVerticalGrid(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            columns = GridCells.Adaptive(minSize = 180.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(worlds) { world ->
                WorldItemView(title = world.name, logo = world.logo) {
                    when (world.key) {
                        "sample_app" -> rootController.present(
                            SampleNavigation.sampleFlow,
                            params = world.name
                        )

                        "pro" -> platformConfiguration.openFlutterModule(world.key)
                        "browser" -> rootController.present(BrowserNavigation.BROWSER_FLOW)
                    }
                }
            }
        }
    }
}

@Composable
private fun WorldItemView(title: String, logo: DrawableResource, onItemClicked: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onItemClicked.invoke() }.padding(8.dp).size(180.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
            Image(
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(logo),
                contentDescription = title
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.0f),
                                Color.Black.copy(alpha = 0.5f)
                            )
                        )
                    )
            )

            Text(
                modifier = Modifier.padding(16.dp).align(Alignment.BottomStart),
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = YSFontFamily()
            )
        }
    }
}

private object Apps {

    val apps = listOf(
        Module(
            key = "pro",
            name = "Яндекс Про",
            logo = Res.drawable.pro_logo,
            tech = ModuleTech.FLUTTER,
        ),
        Module(
            key = "browser",
            name = "Браузер",
            logo = Res.drawable.yabro_logo,
            tech = ModuleTech.KMP,
        ),
        Module(
            key = "sample_app",
            name = "Пример приложения",
            logo = Res.drawable.sample_app_logo,
            tech = ModuleTech.KMP,
        ),
        Module(
            key = "future_app",
            name = "Будущее приложение",
            logo = Res.drawable.future_app_logo,
            tech = ModuleTech.KMP,
        ),
        Module(
            key = "unknown_app",
            name = "Неизвестное приложение",
            logo = Res.drawable.unknown_app_logo,
            tech = ModuleTech.KMP,
        ),
        Module(
            key = "secret_app",
            name = "Секретное приложение",
            logo = Res.drawable.secret_app_logo,
            tech = ModuleTech.KMP,
        )
    ).shuffled(Random.Default).toList()
}