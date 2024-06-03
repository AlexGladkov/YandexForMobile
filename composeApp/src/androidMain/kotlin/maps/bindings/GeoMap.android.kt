package maps.bindings

import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapWindow
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

actual typealias GeoScreenPoint = ScreenPoint
actual typealias GeoCameraPosition = CameraPosition

actual class GeoMap(private val view: MapView) {
    private val listenersMap = mutableMapOf<GeoMapCameraListener, CameraListener>()

    private inline fun <R> withMap(block: Map.() -> R): R {
        return withMapWindow { map.block() }
    }

    private inline fun <R> withMapWindow(block: MapWindow.() -> R): R {
        return view.mapWindow?.let(block)!!
    }

    actual fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint? {
        return withMapWindow { screenToWorld(screenPoint) }
    }

    actual fun addCameraListener(listener: GeoMapCameraListener) {
        val actualListener = listenersMap.getOrPut(listener) {
            CameraListener { _, cameraPosition, _, finished ->
                listener.onCameraPositionChanged(cameraPosition, finished)
            }
        }
        withMap { addCameraListener(actualListener) }
    }

    actual fun removeCameraListener(listener: GeoMapCameraListener) {
        listenersMap.remove(listener)?.let(view.mapWindow.map::removeCameraListener)
    }

    actual fun addCollection(): GeoMapObjectCollection {
        return withMap { mapObjects.addCollection() }
    }

    actual fun removeCollection(collection: GeoMapObjectCollection) {
        withMap { mapObjects.remove(collection) }
    }

    actual fun moveCamera(position: GeoCameraPosition, animated: Boolean) {
        withMap {
            if (animated) {
                move(position, Animation(Animation.Type.LINEAR, 300f), null)
            } else {
                move(position)
            }
        }
    }

    actual fun cameraPosition(): GeoCameraPosition {
        return withMap { cameraPosition }
    }
}

actual fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint = ScreenPoint(x, y)

actual typealias GeoMapObjectCollection = MapObjectCollection

actual typealias GeoMapObject = MapObject

actual typealias GeoPlacemark = PlacemarkMapObject

actual typealias GeoPlacemarkImage = ImageProvider

actual val GeoCameraPosition.point: MapkitPoint
    get() = target

actual fun GeoCameraPosition.withPoint(point: MapkitPoint): GeoCameraPosition {
    return CameraPosition(
        point,
        zoom,
        azimuth,
        tilt,
    )
}