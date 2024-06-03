package maps.bindings

import com.yandex.mapkit.geometry.Geo

actual object GeoUtils {
    actual fun distanceMeters(p1: GeoPoint, p2: GeoPoint): Double {
        return Geo.distance(p1, p2)
    }

    actual fun courseDegrees(from: GeoPoint, to: GeoPoint): Double {
        return Geo.course(from, to)
    }
}