package maps.bindings


interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

expect class GeoScreenPoint

expect fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint

expect class GeoCameraPosition

expect val GeoCameraPosition.point: MapkitPoint
expect fun GeoCameraPosition.withPoint(
    point: MapkitPoint,
): GeoCameraPosition

expect class GeoMap {
    fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint?
    fun addCameraListener(listener: GeoMapCameraListener)
    fun removeCameraListener(listener: GeoMapCameraListener)
    fun addCollection(): GeoMapObjectCollection
    fun removeCollection(collection: GeoMapObjectCollection)
    fun moveCamera(position: GeoCameraPosition, animated: Boolean = false)
    fun cameraPosition(): GeoCameraPosition
}

expect interface GeoMapObjectCollection {
    fun addPlacemark(): GeoPlacemark
}

expect abstract class GeoPlacemarkImage

expect interface GeoMapObject {
    fun setDraggable(draggable: Boolean)
    fun setDragListener(listener: GeoMapObjectDragListener?)
    fun setVisible(visible: Boolean)
}

expect interface GeoPlacemark: GeoMapObject {
    fun getGeometry(): MapkitPoint
    fun setGeometry(point: MapkitPoint)
    fun setIcon(image: GeoPlacemarkImage)
}