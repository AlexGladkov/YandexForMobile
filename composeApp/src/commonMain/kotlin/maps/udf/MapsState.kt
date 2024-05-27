package maps.udf

import maps.bindings.Coordinates

data class MapsState(val screen: MapScreen = MapScreen.ExpectFun())

sealed interface MapScreen {
    data class ExpectFun(
        val contour: EditableContour = EditableContour(),
    ) : MapScreen

    data class ActualFun(
        val referencePoint: Coordinates,
        val contour: RelativeContour,
    ) : MapScreen
}

data class EditableContour(
    val points: List<Coordinates> = emptyList(),
)

data class RelativeContour(
    val positions: List<RelativePosition>,
)

data class RelativePosition(
    val courseRadians: Double,
    val distanceMeters: Double,
)