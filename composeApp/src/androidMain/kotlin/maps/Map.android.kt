package maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.mapview.MapView

@Composable
actual fun Map() {
    AndroidView(
        factory = { context -> MapView(context) },
        update = {}
    )
}