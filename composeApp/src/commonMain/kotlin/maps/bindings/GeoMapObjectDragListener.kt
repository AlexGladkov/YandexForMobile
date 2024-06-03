package maps.bindings

expect interface GeoMapObjectDragListener {
    fun onMapObjectDragStart(mapObject: GeoMapObject)
    fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint)
    fun onMapObjectDragEnd(mapObject: GeoMapObject)
}
