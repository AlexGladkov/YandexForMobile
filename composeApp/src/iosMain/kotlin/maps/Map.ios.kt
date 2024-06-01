package maps

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.YandexMapsMobile.YMKMapView
import maps.bindings.GeoMap
import platform.CoreGraphics.CGRectMake

@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    UIKitView(
        factory = {
            val mapView = YMKMapView(CGRectMake(0.0, 0.0, 0.0, 0.0))

            state.map = GeoMap(mapView)

            mapView
        },
        update = {},
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
    )
}
