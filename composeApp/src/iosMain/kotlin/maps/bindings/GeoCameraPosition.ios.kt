package maps.bindings

import cocoapods.YandexMapsMobile.YMKCameraPosition

actual typealias GeoCameraPosition = YMKCameraPosition

actual val GeoCameraPosition.point: GeoPoint
    get() = target

actual fun GeoCameraPosition.withPoint(point: GeoPoint): GeoCameraPosition {
    return YMKCameraPosition.cameraPositionWithTarget(
        point,
        zoom,
        azimuth,
        tilt,
    )
}