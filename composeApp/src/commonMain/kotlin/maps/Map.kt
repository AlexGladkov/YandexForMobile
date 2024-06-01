package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import maps.bindings.GeoMap
import maps.bindings.GeoScreenPoint
import maps.bindings.MapkitPoint
import maps.bindings.createMapkitPoint
import maps.bindings.lat
import maps.bindings.lon

data class Coordinates(val cachePoint: MapkitPoint) {
    constructor(lat: Double, lon: Double) : this(createMapkitPoint(lat = lat, lon = lon))

    val lat by cachePoint::lat
    val lon by cachePoint::lon
}

data class CameraPosition(
    val center: Coordinates,
    val zoom: Float,
    val azimuth: Float,
)

data class CameraMove(
    val position: CameraPosition,
    val finished: Boolean
)

class MapState(val coordinates: Coordinates? = null) {
    var map by mutableStateOf<GeoMap?>(null)

    companion object {
        val Saver: Saver<MapState, Coordinates> = Saver(
            save = { it.coordinates },
            restore = { MapState(it) },
        )
    }

    fun screenToWorld(screenPoint: GeoScreenPoint): Coordinates? {
        return map?.screenToWorld(screenPoint)?.let(::Coordinates)
    }
}

@Composable
expect fun Map(
    state: MapState = rememberMapState(),
    onCameraMoved: ((CameraMove) -> Unit)? = null
)

@Composable
inline fun rememberMapState(
    key: String? = null,
    crossinline init: MapState.() -> Unit = {}
): MapState = rememberSaveable(key = key, saver = MapState.Saver) {
    MapState().apply(init)
}