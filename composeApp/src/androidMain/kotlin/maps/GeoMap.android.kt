package maps

import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.ScreenPoint as MapkitScreenPoint

actual class GeoMap(private val mapView: MapView) {
    actual fun screenToWorld(screenPoint: ScreenPoint): Coordinates? {
        val point = mapView.mapWindow.screenToWorld(
            MapkitScreenPoint(
                screenPoint.x,
                screenPoint.y,
            )
        )

        return point?.run {
            Coordinates(
                lat = latitude,
                lon = longitude,
            )
        }
    }
}