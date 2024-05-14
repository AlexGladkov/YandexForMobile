import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import browser.BrowserNavigation
import navigation.SampleNavigation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val rootController = LocalRootController.current

    Column {
        listOf("Team A", "Team B").forEach {
            WorldItemView(title = it) {
                rootController.present(SampleNavigation.sampleFlow, params = it)
            }
        }
        WorldItemView("Browser") {
            rootController.present(BrowserNavigation.BROWSER_FLOW)
        }
    }
}

@Composable
private fun ColumnScope.WorldItemView(title: String, onItemClicked: () -> Unit) {
    Row(modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onItemClicked).fillMaxWidth().height(72.dp).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Arrow")
    }
}