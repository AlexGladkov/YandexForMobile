package maps.bindings

expect interface GeoMapObject {
    fun setDraggable(draggable: Boolean)
    fun setDragListener(listener: GeoMapObjectDragListener?)
    fun setVisible(visible: Boolean)
}

expect interface GeoMapObjectCollection {
    fun addPlacemark(): GeoPlacemark
}

expect interface GeoPlacemark: GeoMapObject {
    fun getGeometry(): GeoPoint
    fun setGeometry(point: GeoPoint)
    fun setIcon(image: GeoPlacemarkImage)
}