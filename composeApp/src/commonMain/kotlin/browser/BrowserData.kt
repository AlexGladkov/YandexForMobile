@file:OptIn(ExperimentalResourceApi::class)

package browser

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.browser_contribution_icon
import worlds.composeapp.generated.resources.browser_path_icon
import worlds.composeapp.generated.resources.browser_team_icon
import worlds.composeapp.generated.resources.browser_technology_icon

data class BrowserItem(
    val title: String,
    val backgroundColor: Color,
    val icon: DrawableResource,
    val iconSize: Int,
)

fun browserItems(): List<BrowserItem> {
    return listOf(
        BrowserItem("Команда", Color(0xFFFDFFE5), Res.drawable.browser_team_icon, 140),
        BrowserItem("Путь развития", Color(0xFFE5F5FF), Res.drawable.browser_path_icon, 120),
        BrowserItem("Вклад в общее дело", Color(0xFFE5F5FF), Res.drawable.browser_contribution_icon, 120),
        BrowserItem("Технологии", Color(0xFFFDFFE5), Res.drawable.browser_technology_icon, 120),
    )
}