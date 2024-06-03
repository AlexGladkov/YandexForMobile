package maps.bindings

expect class GeoCameraPosition

expect val GeoCameraPosition.point: GeoPoint
expect fun GeoCameraPosition.withPoint(
    point: GeoPoint,
): GeoCameraPosition