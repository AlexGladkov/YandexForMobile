package maps.bindings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

actual typealias GeoScreenPoint = ScreenPoint
actual typealias GeoCameraPosition = CameraPosition

actual class GeoMap(private val view: MapView) {
    private val listenersMap = mutableMapOf<GeoMapCameraListener, CameraListener>()

    actual fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint? {
        return view.mapWindow.screenToWorld(screenPoint)
    }

    actual fun addCameraListener(listener: GeoMapCameraListener) {
        val actualListener = listenersMap.getOrPut(listener) {
            CameraListener { _, cameraPosition, _, finished ->
                listener.onCameraPositionChanged(cameraPosition, finished)
            }
        }
        view.mapWindow.map.addCameraListener(actualListener)
    }

    actual fun removeCameraListener(listener: GeoMapCameraListener) {
        listenersMap.remove(listener)?.let(view.mapWindow.map::removeCameraListener)
    }

    actual fun addCollection(): GeoMapObjectCollection {
        return view.mapWindow.map.mapObjects.addCollection()
    }

    actual fun removeCollection(collection: GeoMapObjectCollection) {
        view.mapWindow.map.mapObjects.remove(collection)
    }
}

actual fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint = ScreenPoint(x, y)
actual typealias GeoMapObjectCollection = MapObjectCollection

actual typealias GeoPlacemark = PlacemarkMapObject

actual typealias GeoPlacemarkImage = ImageProvider

@Composable
actual fun DrawableResource.readAsGeoPlacemarkImage(): GeoPlacemarkImage {
    return ImageProvider.fromBitmap(imageResource(this).asAndroidBitmap())
}