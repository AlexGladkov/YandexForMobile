package maps

import YSFontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maps.bindings.compose.Map
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

private const val mapSize = 200
private val backgroundPrimary = Color(0xffffffff)
private val corners = RoundedCornerShape(size = 20.dp)

@Composable
fun MapsPresentationScreen() {
    Box(
        modifier = Modifier
            .background(Color(0xfff4f4f4))
            .safeDrawingPadding(),
    ) {

        val state = rememberLazyListState()
        val offset = rememberCurrentOffset(state)

        val maxOffset = with(LocalDensity.current) { (mapSize + 16).dp.toPx() }

        val alpha = if (offset.value < maxOffset) {
            1f
        } else {
            0f
        }

        Card(
            elevation = 0.dp,
            backgroundColor = backgroundPrimary,
            shape = corners,
            modifier = Modifier
                .padding(8.dp)
                .alpha(alpha),
        ) {
            Box(
                modifier = Modifier
                    .height(mapSize.dp)
                    .fillMaxWidth(),
            ) {
                Map()
                Title(text = "Изучить Проекцию", modifier = Modifier.padding(16.dp))
            }
        }

        val controller = LocalRootController.current

        LazyColumn(
            state = state,
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        ) {
            item {
                Box(modifier = Modifier
                    .height((mapSize + 16).dp).fillMaxWidth()
                    .clickable { controller.push(MapsNavigation.HAVE_FUN_WITH_MAP) }) {
                }
            }

            item { Description() }

            item { Navigation() }
            item { DetailedStreets() }
            item { OnlineTransport() }
            item { OrgsInfo() }
            item { Panoramas() }
            item { Buildings3D() }
        }
    }
}

@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}


@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

@Composable
private fun Description() = MapsCard(
    title = "Яндекс Карты",
    description = "Яндекс Карты помогают миллионам людей находить места и выбирать из них подходящие — по отзывам, фотографиям и видео.\n\n" +
            "Строят быстрые и безопасные маршруты на машине, общественном транспорте, велосипеде, самокате или пешком.\n\n" +
            "При этом учитывают ситуацию на дороге и препятствия на пути.",
)

@Composable
private fun Navigation() = MapsCard(
    title = "Навигация",
    description = "Яндекс Карты строят маршруты на городском транспорте, автомобилях, велосипедах, самокатах или пешком. " +
            "Водителей Карты предупреждают о камерах, ограничениях скорости, пробках, перекрытиях, авариях и сложных манёврах. " +
            "Карты также дают информацию об альтернативных вариантах маршрута, ближайших парковках, заправках и платных дорогах. " +
            "Ещё сервис может строить маршруты в будущее — предполагая дорожную ситуацию в определённый день и время.",
)

@Composable
private fun DetailedStreets() = MapsCard(
    title = "Детальные дороги",
    description = "Дороги в Картах в режиме автомобильной навигации отображаются с детальной разметкой — как в городе. " + "" +
            "Видны островки безопасности и парковочные места. " +
            "Рекомендуемая траектория маршрута строится с учётом полос и подсказывает, когда лучше перестроиться. " + "" +
            "Объёмные здания вдоль маршрута также помогают ориентироваться.",
)

@Composable
private fun OnlineTransport() = MapsCard(
    title = "Транспорт онлайн",
    description = "Автобусы, троллейбусы и трамваи движутся по карте в реальном времени, " +
            "а на остановках общественного транспорта есть виртуальное табло с расписанием и временем прибытия каждого маршрута",
)

@Composable
private fun OrgsInfo() = MapsCard(
    title = "Информация об организациях",
    description = "Яндекс Карты находят нужные места по названиям и контексту. " + "" +
            "А нейросетевой поиск поможет найти место по сложному запросу (например, «ресторан с красивым видом» ). " +
            "Про каждую организацию, заведение или место можно получить всю доступную информацию, включая фотографии и видео, время работы, контакты и отзывы.",
)

@Composable
private fun Panoramas() = MapsCard(
    title = "Панорамы",
    description = "С помощью панорам в Яндекс Картах можно устраивать виртуальные прогулки по городу, " +
            "рассматривать достопримечательности или труднодоступные места. " +
            "В Яндекс Картах есть уличные и пешеходные панорамы. " +
            "А ещё можно заглянуть во дворы или труднодоступные районы города и пройтись по отдалённым местам, " +
            "отснятым с помощью автоматизированных панорамомобилей и операторов, снимающих камерой с обзором 360 градусов. " +
            "На панорамах городов подписаны улицы, расставлены таблички с номерами домов и размечены названия организаций.",
)

@Composable
private fun Buildings3D() = MapsCard(
    title = "3D-здания",
    description = "В Картах можно найти цветные трёхмерные модели достопримечательностей " +
            "Москвы, Санкт-Петербурга, Уфы, Екатеринбурга и Новосибирска. " +
            "Здания прорисованы с высокой детализацией и передают основные архитектурные особенности, цвета и тени.",
)


@Composable
private fun MapsCard(title: String, description: String) {

    Card(
        elevation = 4.dp,
        backgroundColor = Color(0xffffffff),
        shape = corners,
        modifier = Modifier.padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Title(title)

            VerticalSpace(12)

            CardDescription(description)
        }
    }
}

@Composable
private fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = YSFontFamily(),
        modifier = modifier,
    )
}

@Composable
private fun CardDescription(text: String) = Text(
    text = text,
    fontSize = 16.sp,
    fontWeight = FontWeight.Light,
    fontFamily = YSFontFamily(),
)

@Composable
private fun VerticalSpace(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}