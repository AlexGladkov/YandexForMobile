package maps.bindings



interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

expect class GeoScreenPoint

expect class GeoCameraPosition

expect class GeoMap {
    fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint?
    fun addCameraListener(listener: GeoMapCameraListener)
    fun removeCameraListener(listener: GeoMapCameraListener)
}