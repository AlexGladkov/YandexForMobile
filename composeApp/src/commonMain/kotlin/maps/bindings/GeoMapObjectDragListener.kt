package maps.bindings

expect interface GeoMapObjectDragListener {
    fun onMapObjectDragStart(mapObject: GeoMapObject)
    fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint)
    fun onMapObjectDragEnd(mapObject: GeoMapObject)
}

abstract class SimpleDragListener : GeoMapObjectDragListener {
    override fun onMapObjectDragStart(mapObject: GeoMapObject) {
    }

    override fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint) {
    }

    override fun onMapObjectDragEnd(mapObject: GeoMapObject) {
    }
}

@Suppress("FunctionName")
fun DragsPositionsListener(block: (GeoPoint) -> Unit): GeoMapObjectDragListener =
    object : SimpleDragListener() {
        override fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint) {
            block(point)
        }
    }