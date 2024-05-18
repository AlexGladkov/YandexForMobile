import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import browser.BrowserNavigation
import di.Inject
import models.Module
import models.ModuleTech
import navigation.SampleNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kodein.di.instance
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
@Preview
fun App() {
    val rootController = LocalRootController.current

    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        listOf(
            Module("team_a", "Team A"),
            Module("pro", "Яндекс Про", ModuleTech.FLUTTER)
        ).forEach {
            WorldItemView(title = it.name) {
                when (it.tech) {
                    ModuleTech.KMP -> {
                        rootController.present(SampleNavigation.sampleFlow, params = it.name)
                    }

                    ModuleTech.FLUTTER -> {
                        println("Open Flutter for ${it.name}")
                        val platformConfiguration = Inject.di.instance<PlatformConfiguration>()
                        platformConfiguration.openFlutterModule(it.key)
                    }
                }
            }
        }
        WorldItemView("Browser") {
            rootController.present(BrowserNavigation.BROWSER_FLOW)
        }
    }
}

@Composable
private fun WorldItemView(title: String, onItemClicked: () -> Unit) {
    Row(modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onItemClicked).fillMaxWidth().height(72.dp).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Arrow")
    }
}