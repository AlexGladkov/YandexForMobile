package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

data class Coordinates(val lat: Double, val lon: Double)

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

    fun screenToWorld(screenPoint: ScreenPoint): Coordinates? {
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