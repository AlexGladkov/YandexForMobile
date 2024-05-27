package maps.bindings

import cocoapods.YandexMapsMobile.YMKScreenPoint

actual typealias GeoScreenPoint = YMKScreenPoint

actual fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint {
    return YMKScreenPoint.screenPointWithX(x = x, y = y)
}