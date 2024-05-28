package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import maps.data.Australia
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    var savedCameraListener by remember { mutableStateOf<CameraListener?>(null) }
    val imageProvider =
        ImageProvider.fromBitmap(imageResource(Res.drawable.map_dot).asAndroidBitmap())
    var collection by remember { mutableStateOf<MapObjectCollection?>(null) }
    AndroidView(
        factory = { context ->
            MapView(context).also { mapView ->
                if (onCameraMoved != null) {
                    val listener = CameraListener { _, position, _, finished ->
                        onCameraMoved(
                            CameraMove(
                                center = Position(
                                    lat = position.target.latitude,
                                    lon = position.target.longitude,
                                ),
                                finished = finished,
                            )
                        )
                    }

                    savedCameraListener = listener
                    mapView.mapWindow.map.addCameraListener(listener)
                    state.map = GeoMap(mapView)

                    val placemarks = mapView.mapWindow.map.mapObjects.addCollection().also {
                        collection = it
                    }

                    for (position in Australia.positions) {
                        placemarks.addPlacemark().apply {
                            geometry = Point(position.lat, position.lon)
                            setIcon(imageProvider)
                        }
                    }
                }
            }
        },
        update = {},
        onRelease = {
            savedCameraListener?.let(it.mapWindow.map::removeCameraListener)
            savedCameraListener = null
            state.map = null
        }
    )
}