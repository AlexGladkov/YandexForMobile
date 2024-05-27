package maps.bindings

import com.yandex.mapkit.geometry.Point

actual typealias GeoPoint = Point

actual fun makeGeoPoint(lat: Double, lon: Double): GeoPoint {
    return GeoPoint(lat, lon)
}

actual val GeoPoint.lat: Double
    get() = latitude
actual val GeoPoint.lon: Double
    get() = longitude