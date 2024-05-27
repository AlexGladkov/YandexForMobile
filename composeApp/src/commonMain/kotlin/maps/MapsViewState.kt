package maps

import maps.udf.MapScreen


data class MapsViewState(
    val isMapDraggable: Boolean,
    val screen: MapScreen,
)