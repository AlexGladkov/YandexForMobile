package maps.bindings

import com.yandex.mapkit.ScreenPoint

actual typealias GeoScreenPoint = ScreenPoint

actual fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint = ScreenPoint(x, y)