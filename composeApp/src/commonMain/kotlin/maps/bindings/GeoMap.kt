package maps.bindings


expect class GeoMap {
    fun screenToWorld(screenPoint: GeoScreenPoint): GeoPoint?
    fun addCameraListener(listener: GeoMapCameraListener)
    fun removeCameraListener(listener: GeoMapCameraListener)
    fun addCollection(): GeoMapObjectCollection
    fun removeCollection(collection: GeoMapObjectCollection)
    fun moveCamera(position: GeoCameraPosition, animated: Boolean = false)
    fun cameraPosition(): GeoCameraPosition
}