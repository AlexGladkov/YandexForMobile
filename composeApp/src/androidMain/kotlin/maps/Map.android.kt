package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import maps.bindings.CameraListenerLambdaWrapper
import maps.bindings.GeoMap
import maps.bindings.GeoMapCameraListener
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoUtils
import maps.data.Australia
import org.jetbrains.compose.resources.imageResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot
import com.yandex.mapkit.map.CameraPosition as MapkitCameraPosition

data class PlacemarkData(
    val icon: GeoPlacemarkImage,
    val position: Coordinates,
)

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