package maps.bindings

import cocoapods.YandexMapsMobile.YMKCourse
import cocoapods.YandexMapsMobile.YMKDistance

actual object GeoUtils {
    actual fun distanceMeters(p1: MapkitPoint, p2: MapkitPoint): Double {
        return YMKDistance(p1, p2)
    }

    actual fun courseDegrees(from: MapkitPoint, to: MapkitPoint): Double {
        return YMKCourse(from, to)
    }
}