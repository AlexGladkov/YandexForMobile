@file:OptIn(ExperimentalForeignApi::class)

package maps.bindings

import cocoapods.YandexMapsMobile.YMKPoint
import kotlinx.cinterop.ExperimentalForeignApi

actual typealias MapkitPoint = YMKPoint


actual fun createMapkitPoint(lat: Double, lon: Double): MapkitPoint {
    return MapkitPoint.pointWithLatitude(latitude = lat, longitude = lon)
}

actual val MapkitPoint.lat: Double
    get() = latitude
actual val MapkitPoint.lon: Double
    get() = longitude