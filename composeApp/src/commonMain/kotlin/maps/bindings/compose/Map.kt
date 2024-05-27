package maps.bindings.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import maps.bindings.CameraMove
import maps.bindings.Coordinates
import maps.bindings.GeoMap
import maps.bindings.GeoScreenPoint
import maps.bindings.makeGeoScreenPoint


class MapState(val coordinates: Coordinates? = null) {
    var map by mutableStateOf<GeoMap?>(null)

    companion object {
        val Saver: Saver<MapState, Coordinates> = Saver(
            save = { it.coordinates },
            restore = { MapState(it) },
        )
    }

    fun screenToWorld(x: Float, y: Float): Coordinates? {
        return map?.screenToWorld(makeGeoScreenPoint(x = x, y = y))?.let(::Coordinates)
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

