package maps.bindings

expect interface GeoMapObjectDragListener {
    fun onMapObjectDragStart(mapObject: GeoMapObject)
    fun onMapObjectDrag(mapObject: GeoMapObject, point: MapkitPoint)
    fun onMapObjectDragEnd(mapObject: GeoMapObject)
}
