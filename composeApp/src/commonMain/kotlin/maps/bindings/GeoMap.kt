package maps.bindings

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource


interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

expect class GeoScreenPoint

expect fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint

expect class GeoCameraPosition

expect class GeoMap {
    fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint?
    fun addCameraListener(listener: GeoMapCameraListener)
    fun removeCameraListener(listener: GeoMapCameraListener)
    fun addCollection(): GeoMapObjectCollection
    fun removeCollection(collection: GeoMapObjectCollection)
}

expect interface GeoMapObjectCollection
expect abstract class GeoPlacemarkImage

expect interface GeoPlacemark {
    fun setGeometry(point: MapkitPoint)
    fun setIcon(image: GeoPlacemarkImage)
}

@Composable
expect fun DrawableResource.readAsGeoPlacemarkImage(): GeoPlacemarkImage