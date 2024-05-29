import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.ys_text_bold
import worlds.composeapp.generated.resources.ys_text_light
import worlds.composeapp.generated.resources.ys_text_medium
import worlds.composeapp.generated.resources.ys_text_regular

@Composable
fun YSFontFamily() = FontFamily(
    Font(Res.font.ys_text_light, weight = FontWeight.Light),
    Font(Res.font.ys_text_regular, weight = FontWeight.Normal),
    Font(Res.font.ys_text_medium, weight = FontWeight.Medium),
    Font(Res.font.ys_text_bold, weight = FontWeight.Bold)
)
