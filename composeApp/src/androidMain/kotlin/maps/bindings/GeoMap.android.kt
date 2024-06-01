package maps.bindings

import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

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
        listenersMap[listener]?.let(view.mapWindow.map::removeCameraListener)
    }
}