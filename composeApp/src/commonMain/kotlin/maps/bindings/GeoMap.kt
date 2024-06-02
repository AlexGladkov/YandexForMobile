package maps.bindings

import androidx.compose.ui.graphics.ImageBitmap


interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

expect class GeoScreenPoint

expect fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint

expect class GeoCameraPosition
expect val GeoCameraPosition.point: MapkitPoint

expect class GeoMap {
    fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint?
    fun addCameraListener(listener: GeoMapCameraListener)
    fun removeCameraListener(listener: GeoMapCameraListener)
    fun addCollection(): GeoMapObjectCollection
    fun removeCollection(collection: GeoMapObjectCollection)
}

expect interface GeoMapObjectCollection {
    fun addPlacemark(): GeoPlacemark
}
expect abstract class GeoPlacemarkImage

expect interface GeoPlacemark {
    fun setGeometry(point: MapkitPoint)
    fun setIcon(image: GeoPlacemarkImage)
}

expect fun ImageBitmap.asGeoPlacemarkImage(): GeoPlacemarkImage