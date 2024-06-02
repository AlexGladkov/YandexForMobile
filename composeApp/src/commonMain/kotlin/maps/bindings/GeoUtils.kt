package maps.bindings

expect object GeoUtils {
    fun distanceMeters(p1: MapkitPoint, p2: MapkitPoint): Double
    fun courseDegrees(from: MapkitPoint, to: MapkitPoint): Double
}