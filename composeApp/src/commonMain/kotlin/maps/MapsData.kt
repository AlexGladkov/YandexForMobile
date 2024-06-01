@file:OptIn(ExperimentalResourceApi::class)

package maps

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.browser_summarize_icon

data class MapsItem(
    val title: String,
    val backgroundColor: Color,
    val icon: DrawableResource,
    val iconSize: Int,
)

fun mapsItems(): List<MapsItem> {
    return listOf(
        MapsItem("Команда", Color(0xFFFDFFE5), Res.drawable.browser_summarize_icon, 140),
        MapsItem("Путь развития", Color(0xFFE5F5FF), Res.drawable.browser_summarize_icon, 120),
        MapsItem("Вклад в общее дело", Color(0xFFE5F5FF), Res.drawable.browser_summarize_icon, 120),
        MapsItem("Технологии", Color(0xFFFDFFE5), Res.drawable.browser_summarize_icon, 120),
    )
}