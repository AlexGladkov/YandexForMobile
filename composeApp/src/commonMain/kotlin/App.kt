import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.*
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
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.ImageRequest
import core.getAsyncImageLoader
import di.Inject
import models.Module
import models.ModuleTech
import navigation.SampleNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kodein.di.instance
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    val rootController = LocalRootController.current
    val platformConfiguration by derivedStateOf { Inject.di.instance<PlatformConfiguration>() }
    val worlds = remember {
        listOf(
            Module(
                key = "team_a",
                name = "Team A",
                imageUrl = "https://media.themoviedb.org/t/p/w440_and_h660_face/czembW0Rk1Ke7lCJGahbOhdCuhV.jpg",
                tech = ModuleTech.KMP
            ),
            Module(
                key = "pro",
                name = "Яндекс Про",
                imageUrl = "https://rozetked.me/images/uploads/Q6iN2E5sX03i.jpg",
                tech = ModuleTech.FLUTTER
            ),
            Module(
                key = "browser",
                name = "Browser",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLoHt6lkYJWQW7zGH8cCVvICjHOLdEPWd_gL9P0JHbYw&s",
                tech = ModuleTech.KMP
            )
        )
    }

    Column {
        Spacer(modifier = Modifier.height(72.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Я!ВСЕ", fontSize = 28.sp, fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            columns = GridCells.Adaptive(minSize = 172.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(worlds) { world ->
                WorldItemView(title = world.name, imageUrl = world.imageUrl) {
                    when (world.key) {
                        "team_a" -> rootController.present(
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
private fun WorldItemView(title: String, imageUrl: String, onItemClicked: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onItemClicked.invoke() }.padding(8.dp).size(172.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
            val painter =
                rememberAsyncImagePainter(
                    model = imageUrl,
                    contentScale = ContentScale.FillBounds,
                    onError = { error ->
                        println("Error ${error.result.throwable.message}")
                    })

            Image(
                modifier = Modifier.size(172.dp),
                contentScale = ContentScale.Crop,
                painter = painter,
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
                fontWeight = FontWeight.Bold
            )
        }
    }
}