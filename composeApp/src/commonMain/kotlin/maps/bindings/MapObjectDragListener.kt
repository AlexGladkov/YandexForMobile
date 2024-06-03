package maps.bindings

expect interface MapObjectDragListener {
    /**
     * Raised when dragging mode is active for the given map object.
     */
    fun onMapObjectDragStart(mapObject: com.yandex.mapkit.kmp.map.MapObject): Unit

    /**
     * Raised when the user is moving a finger and the map object follows
     * it.
     */
    fun onMapObjectDrag(
        mapObject: com.yandex.mapkit.kmp.map.MapObject,
        point: com.yandex.mapkit.kmp.geometry.Point,
    ): Unit

    /**
     * Raised when the user released the tap.
     */
    fun onMapObjectDragEnd(mapObject: com.yandex.mapkit.kmp.map.MapObject): Unit
}
