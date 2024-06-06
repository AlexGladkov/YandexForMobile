package maps.bindings

import com.yandex.mapkit.map.CameraPosition

actual typealias GeoCameraPosition = CameraPosition

actual val GeoCameraPosition.point: GeoPoint
    get() = target

actual fun GeoCameraPosition.withPoint(point: GeoPoint): GeoCameraPosition {
    return CameraPosition(
        point,
        zoom,
        azimuth,
        tilt,
    )
}