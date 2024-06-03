package maps

import maps.bindings.Coordinates
import maps.udf.EditableContour
import maps.udf.RelativeContour


sealed interface MapViewState

data class MapEditContourViewState(
    val contour: EditableContour,
    val isMapDraggable: Boolean,
    val showRevertButton: Boolean,
) : MapViewState

data class MapPlayWithMercatorViewState(
    val referencePoint: Coordinates,
    val contour: RelativeContour,
) : MapViewState