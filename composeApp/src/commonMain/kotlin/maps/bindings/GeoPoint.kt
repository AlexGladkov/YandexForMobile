package maps.bindings

expect class GeoPoint

expect fun makeGeoPoint(lat: Double, lon: Double): GeoPoint

expect val GeoPoint.lat: Double
expect val GeoPoint.lon:Double
