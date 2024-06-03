package maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import maps.bindings.GeoCameraPosition
import maps.bindings.GeoMap
import maps.bindings.GeoScreenPoint
import maps.bindings.MapkitPoint
import maps.bindings.createMapkitPoint
import maps.bindings.lat
import maps.bindings.lon

class Coordinates(val cachePoint: MapkitPoint) {
    constructor(lat: Double, lon: Double) : this(createMapkitPoint(lat = lat, lon = lon))

    val lat by cachePoint::lat
    val lon by cachePoint::lon

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinates) return false

        return other.lat == lat && other.lon == lon
    }

    override fun hashCode(): Int {
        return lat.hash() * 31 + lon.hash()
    }

    private fun Double.hash(): Int {
        val bits = toBits()
        return (bits shr 32).toInt() xor bits.toInt()
    }
}

data class CameraMove(
    val position: GeoCameraPosition,
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