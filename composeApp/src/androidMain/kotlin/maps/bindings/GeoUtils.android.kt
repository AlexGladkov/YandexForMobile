package maps.bindings

import com.yandex.mapkit.geometry.Geo

actual object GeoUtils {
    actual fun distanceMeters(p1: MapkitPoint, p2: MapkitPoint): Double {
        return Geo.distance(p1, p2)
    }

    actual fun courseDegrees(from: MapkitPoint, to: MapkitPoint): Double {
        return Geo.course(from, to)
    }
}