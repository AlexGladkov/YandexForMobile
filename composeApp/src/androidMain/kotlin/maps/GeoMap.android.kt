package maps

import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.ScreenPoint as MapkitScreenPoint

actual class GeoMap(private val mapView: MapView) {
    actual fun screenToWorld(screenPoint: ScreenPoint): Position? {
        val point = mapView.mapWindow.screenToWorld(
            MapkitScreenPoint(
                screenPoint.x,
                screenPoint.y,
            )
        )

        return point?.run {
            Position(
                lat = latitude,
                lon = longitude,
            )
        }
    }
}