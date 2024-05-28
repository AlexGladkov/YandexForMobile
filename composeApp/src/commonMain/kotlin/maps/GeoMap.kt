package maps

data class ScreenPoint(val x: Float, val y: Float)

expect class GeoMap {
    fun screenToWorld(screenPoint: ScreenPoint): Coordinates?
}