package maps.bindings

import cocoapods.YandexMapsMobile.YMKCourse
import cocoapods.YandexMapsMobile.YMKDistance

actual object GeoUtils {
    actual fun distanceMeters(p1: GeoPoint, p2: GeoPoint): Double {
        return YMKDistance(p1, p2)
    }

    actual fun courseDegrees(from: GeoPoint, to: GeoPoint): Double {
        return YMKCourse(from, to)
    }
}