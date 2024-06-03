package maps

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.YandexMapsMobile.YMKMapView
import maps.bindings.CameraListenerLambdaWrapper
import maps.bindings.CameraMove
import maps.bindings.GeoMap
import maps.bindings.GeoMapCameraListener
import platform.CoreGraphics.CGRectMake

@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    var savedCameraListener by remember { mutableStateOf<GeoMapCameraListener?>(null) }
    UIKitView(
        factory = {
            val mapView = YMKMapView(CGRectMake(0.0, 0.0, 0.0, 0.0))

            val map = GeoMap(mapView).also { state.map = it }

            onCameraMoved?.let(::CameraListenerLambdaWrapper)?.let { listener ->
                savedCameraListener = listener
                map.addCameraListener(listener)
            }

            mapView
        },
        update = {},
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        onRelease = {
            savedCameraListener?.let { listener ->
                state.map?.run { removeCameraListener(listener) }
            }
            savedCameraListener = null
            state.map = null
        }
    )
}
