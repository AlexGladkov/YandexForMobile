package maps.bindings

expect object GeoUtils {
    fun distanceMeters(p1: GeoPoint, p2: GeoPoint): Double
    fun courseDegrees(from: GeoPoint, to: GeoPoint): Double
}