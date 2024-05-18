@file:OptIn(ExperimentalResourceApi::class)

package browser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.browser_background

private val browserData = browserItems()

@Composable
fun BrowserScreen() {
    Box(modifier = Modifier.background(Color(0x88EEEEEE))) {
        Image(
            painter = painterResource(Res.drawable.browser_background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp).windowInsetsPadding(WindowInsets.systemBars)) {
            Text(text = "Яндекс Браузер", fontSize = 28.sp, fontWeight = Medium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Яндекс Браузер – одно из крупнейших приложений Яндекса, " +
                        "быстрый и удобной браузер с нейросетями, Алисой, блокировкой рекламы " +
                        "и возможностью синхронизировать пароли и закладки между устройствами. ",
                fontSize = 18.sp,
                fontWeight = Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(browserData.size) { index ->
                        AppItem(browserData[index])
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun AppItem(browserItem: BrowserItem) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .height(200.dp)
            .background(browserItem.backgroundColor),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(browserItem.icon),
                modifier = Modifier.size(browserItem.iconSize.dp),
                contentDescription = "Иконка для ${browserItem.title}",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = browserItem.title,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 12.dp)
        )
    }
}
