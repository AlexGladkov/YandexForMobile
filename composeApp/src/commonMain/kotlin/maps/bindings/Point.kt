@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package maps.bindings

expect class MapkitPoint

expect fun createMapkitPoint(lat: Double, lon: Double): MapkitPoint

expect val MapkitPoint.lat: Double
expect val MapkitPoint.lon:Double
