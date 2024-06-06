package maps.bindings.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.mapview.MapView
import maps.bindings.CameraListenerLambdaWrapper
import maps.bindings.CameraMove
import maps.bindings.Coordinates
import maps.bindings.GeoMap
import maps.bindings.GeoMapCameraListener
import maps.bindings.GeoPlacemarkImage

@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    var savedCameraListener by remember { mutableStateOf<GeoMapCameraListener?>(null) }
    AndroidView(
        factory = { context ->
            MapView(context).also { mapView ->
                val map = GeoMap(mapView).also { state.map = it }

                onCameraMoved?.let(::CameraListenerLambdaWrapper)?.let { listener ->
                    savedCameraListener = listener
                    map.addCameraListener(listener)
                }
            }
        },
        update = {},
        onRelease = {
            savedCameraListener?.let { listener ->
                state.map?.run { removeCameraListener(listener) }
            }
            savedCameraListener = null
            state.map = null
        }
    )
}