package maps

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.YandexMapsMobile.YMKMapView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    UIKitView(
        factory = { YMKMapView(CGRectMake(0.0, 0.0, 0.0, 0.0)) },
        update = {},
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
    )
}
