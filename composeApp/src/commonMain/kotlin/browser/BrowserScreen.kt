package browser

import PlatformConfiguration
import YSFontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.Inject
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.instance
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.browser_background
import worlds.composeapp.generated.resources.browser_summarize_icon
import worlds.composeapp.generated.resources.browser_translate_icon

@Composable
fun BrowserScreen() {
    Box {
        Image(
            painter = painterResource(Res.drawable.browser_background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop
        )
        LazyColumn(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
            item { Title() }

            item { NeuroBrowser() }

            item { StrongTeam() }

            item { OpenSource() }

            item { FastAndSecure() }

            item { Public() }
        }
    }
}

@Composable
private fun Title() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Яндекс Браузер",
            fontSize = 28.sp,
            fontWeight = Medium,
            fontFamily = YSFontFamily()
        )
        VerticalSpace(12)

        Text(
            text = "Яндекс Браузер – одно из крупнейших приложений Яндекса, " +
                    "быстрый и удобной браузер с нейросетями, Алисой, блокировкой рекламы " +
                    "и возможностью синхронизировать пароли и закладки между устройствами. ",
            fontSize = 18.sp,
            fontWeight = Light,
            fontFamily = YSFontFamily()
        )
    }
}

@Composable
private fun NeuroBrowser() {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Вся мощь нейросетей Яндекса",
                fontSize = 20.sp,
                fontWeight = Medium,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(12)
            Row {
                Image(
                    painter = painterResource(Res.drawable.browser_translate_icon),
                    contentDescription = "Browser translate power",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
                HorizontalSpace(8)
                Text(
                    text = "Мы научили браузер переводить для вас видео, сайты и даже текст на картинках, " +
                            "чтобы вам было проще работать с разными языками.",
                    fontSize = 16.sp,
                    fontWeight = Light,
                    fontFamily = YSFontFamily()
                )
            }
            VerticalSpace(8)
            Row {
                Image(
                    painter = painterResource(Res.drawable.browser_summarize_icon),
                    contentDescription = "Browser summarize power",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
                HorizontalSpace(8)
                Text(
                    text = "Браузер помогает сэкономить время и с помощью YandexGPT " +
                            "пересказывает длинные тексты и видео, чтобы вы сразу понимали главное.",
                    fontSize = 16.sp,
                    fontWeight = Light,
                    fontFamily = YSFontFamily()
                )
            }
        }
    }
}

@Composable
private fun StrongTeam() {
    Column(modifier = Modifier.padding(16.dp)) {
        VerticalSpace(4)
        Text(
            text = "Люди",
            fontSize = 20.sp,
            fontWeight = Medium,
            fontFamily = YSFontFamily()
        )
        VerticalSpace(12)

        Text(
            text = "У нас большая команда сильных разработчиков, которые обладают большой экспертизой в разных областях: " +
                    "от ядра Chromium и инфраструктуры до Backend-Driven UI и перформанса. " +
                    "Всегда найдется разработчик, у кого можно поучиться, обсудить сложную идею и решения. ",
            fontSize = 18.sp,
            fontWeight = Light,
            fontFamily = YSFontFamily()
        )
    }
}

@Composable
private fun OpenSource() {
    val uriHandler = LocalUriHandler.current
    Card {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Text(
                text = "Создаем и развиваем технологии",
                fontSize = 20.sp,
                fontWeight = Medium,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(12)

            Text(
                text = "Для наших задач не всегда хватает готовых решений, поэтому мы не боимся " +
                        "создавать новые, а после всегда стараемся сделать их доступными для всех. " +
                        "Любим и вкладываемся в Open source.",
                fontSize = 16.sp,
                fontWeight = Light,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(16)

            Text(
                text = "DivKit",
                fontSize = 18.sp,
                fontWeight = Light,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(4)
            Text(
                text = "Backend-Driven UI фреймворк для Android, iOS, Flutter и Web. " +
                        "Позволяет получать верстку для приложений с сервера и обновлять UI " +
                        "без выпуска новых версий.",
                fontSize = 16.sp,
                fontWeight = Light,
                fontFamily = YSFontFamily()
            )
            TextButton(
                onClick = { uriHandler.openUri("https://github.com/divkit/divkit") },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFF25AB5),
                    backgroundColor = Color.Transparent
                ),
                content = {
                    Text(
                        "Перейти на Github",
                        fontSize = 16.sp,
                        fontWeight = Light,
                        fontFamily = YSFontFamily()
                    )
                }
            )
            VerticalSpace(8)

            Text(
                text = "Yatagan",
                fontSize = 18.sp,
                fontWeight = Bold,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(4)
            Text(
                text = "DI-фреймворк, совместимый по API с Dagger, позволяющий значительно ускорить " +
                        "сборку Android-проекта и расширяющий возможности DI.",
                fontSize = 16.sp,
                fontWeight = Light,
                fontFamily = YSFontFamily()
            )
            TextButton(
                onClick = { uriHandler.openUri("https://github.com/yandex/yatagan") },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFF25AB5),
                    backgroundColor = Color.Transparent
                ),
                content = {
                    Text(
                        "Перейти на Github",
                        fontSize = 16.sp,
                        fontWeight = Light,
                        fontFamily = YSFontFamily()
                    )
                }
            )
        }
    }
}

@Composable
private fun FastAndSecure() {
    Column(modifier = Modifier.padding(16.dp)) {
        VerticalSpace(4)
        Text(
            text = "Быстрый и безопасный",
            fontSize = 20.sp,
            fontWeight = Medium,
            fontFamily = YSFontFamily()
        )
        VerticalSpace(12)

        Text(
            text = "Мы много и усилий тратим на то, чтобы сделать наш браузер самым быстрым и удобным. " +
                    "\nА также мы обучили нейросети распознавать фишинговые сайты и предупреждать пользователя об этом.",
            fontSize = 18.sp,
            fontWeight = Light,
            fontFamily = YSFontFamily()
        )
    }
}

@Composable
private fun Public() {
    Card {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Text(
                text = "Активно делимся экспертизой",
                fontSize = 20.sp,
                fontWeight = Medium,
                fontFamily = YSFontFamily()
            )
            VerticalSpace(12)

            Text(
                text = "Мы любим и считаем важным рассказывать про наш опыт и решения вовне, " +
                        "поэтому стараемся много выступать на конференциях и митапах, " +
                        "пишем технические статьи и вкладываемся в рост разработчиков и сообщества.",
                fontSize = 16.sp,
                fontWeight = Light,
                fontFamily = YSFontFamily()
            )
            TextButton(
                onClick = {
                    val platformConfiguration = Inject.di.instance<PlatformConfiguration>()
                    platformConfiguration.openBrowserDivKitScreen()
                },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFF25AB5),
                    backgroundColor = Color.Transparent
                ),
                content = {
                    Text(
                        "Смотреть подробнее",
                        fontSize = 16.sp,
                        fontWeight = Light,
                        fontFamily = YSFontFamily()
                    )
                }
            )
        }
    }
}

@Composable
private fun Card(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .shadow(elevation = 10.dp)
            .fillMaxWidth()
            .background(Color.White),
    ) {
        content()
    }
}

@Composable
private fun VerticalSpace(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
private fun HorizontalSpace(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}
