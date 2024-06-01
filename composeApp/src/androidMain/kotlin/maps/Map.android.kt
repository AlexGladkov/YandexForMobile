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
import maps.bindings.GeoMap
import maps.bindings.GeoUtils
import maps.data.Australia
import org.jetbrains.compose.resources.imageResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot
import com.yandex.mapkit.map.CameraPosition as MapkitCameraPosition

@Composable
actual fun Map(state: MapState, onCameraMoved: ((CameraMove) -> Unit)?) {
    var savedCameraListener by remember { mutableStateOf<CameraListener?>(null) }
    val imageProvider =
        ImageProvider.fromBitmap(imageResource(Res.drawable.map_dot).asAndroidBitmap())
    var collection by remember { mutableStateOf<MapObjectCollection?>(null) }
    AndroidView(
        factory = { context ->
            val initialCenter = Point(
                Australia.center.lat,
                Australia.center.lon,
            )
            val relatives =
                GeoUtils.calculateRelativeContour(Australia.center, Australia.coordinates)

            MapView(context).also { mapView ->
                if (onCameraMoved != null) {
                    state.map = GeoMap(mapView)

                    val placemarksCollection =
                        mapView.mapWindow.map.mapObjects.addCollection().also {
                            collection = it
                        }

                    val placemarks = Australia.coordinates.map { position ->
                        placemarksCollection.addPlacemark().apply {
                            geometry = Point(position.lat, position.lon)
                            setIcon(imageProvider)
                        }
                    }

                    val listener = CameraListener { _, position, _, finished ->
                        onCameraMoved(
                            CameraMove(
                                position = CameraPosition(
                                    center = Coordinates(
                                        lat = position.target.latitude,
                                        lon = position.target.longitude,
                                    ),
                                    zoom = position.zoom,
                                    azimuth = position.azimuth,
                                ),
                                finished = finished,
                            )
                        )

                        val newCenter = position.target.run {
                            Coordinates(lat = latitude, lon = longitude)
                        }

                        relatives.positions.forEachIndexed { index, relativePosition ->
                            val newCoordinate = DirectProblemSolver.solveDirectProblem(
                                newCenter,
                                relativePosition.courseRadians,
                                relativePosition.distanceMeters,
                            )
                            placemarks[index].geometry = Point(
                                newCoordinate.lat,
                                newCoordinate.lon,
                            )
                        }
                    }

                    savedCameraListener = listener
                    mapView.mapWindow.map.addCameraListener(listener)

                    mapView.mapWindow.map.move(
                        MapkitCameraPosition(
                            initialCenter,
                            Australia.zoom,
                            0f,
                            0f,
                        )
                    )
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