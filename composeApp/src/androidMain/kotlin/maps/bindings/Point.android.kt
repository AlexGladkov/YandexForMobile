@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package maps.bindings

import com.yandex.mapkit.geometry.Point

actual typealias MapkitPoint = Point

actual fun createMapkitPoint(lat: Double, lon: Double): MapkitPoint {
    return MapkitPoint(lat, lon)
}

actual val MapkitPoint.lat: Double
    get() = latitude
actual val MapkitPoint.lon: Double
    get() = longitude