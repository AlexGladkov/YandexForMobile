@file:OptIn(ExperimentalForeignApi::class)

package maps.bindings

import cocoapods.YandexMapsMobile.YMKPoint
import kotlinx.cinterop.ExperimentalForeignApi

actual typealias GeoPoint = YMKPoint

actual fun makeGeoPoint(lat: Double, lon: Double): GeoPoint {
    return YMKPoint.pointWithLatitude(latitude = lat, longitude = lon)
}

actual val GeoPoint.lat: Double
    get() = latitude
actual val GeoPoint.lon: Double
    get() = longitude