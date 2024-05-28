package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

data class Position(val lat: Double, val lon: Double)

data class CameraMove(val center: Position, val finished: Boolean)

class MapState(val position: Position? = null) {
    var map by mutableStateOf<GeoMap?>(null)

    companion object {
        val Saver: Saver<MapState, Position> = Saver(
            save = { it.position },
            restore = { MapState(it) },
        )
    }

    fun screenToWorld(screenPoint: ScreenPoint): Position? {
        return map?.screenToWorld(screenPoint)
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