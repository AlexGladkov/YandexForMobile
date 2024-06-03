package maps.udf

import maps.bindings.Coordinates

data class MapsState(val screen: MapScreen = MapScreen.ExpectFun())

sealed interface MapScreen {
    data class ExpectFun(
        val mode: Mode = Mode.DrawContour,
        val contour: EditableContour = EditableContour(),
    ) : MapScreen {
        enum class Mode {
            DragMap,
            DrawContour,
        }
    }

    data class ActualFun(
        val referencePoint: Coordinates,
        val contour: RelativeContour,
    ) : MapScreen
}

data class EditableContour(
    val currentPart: List<Coordinates>? = null,
    val parts: List<List<Coordinates>> = emptyList(),
) {
    val points by lazy {
        val flattened = parts.flatten()
        if (currentPart == null) {
            flattened
        } else {
            flattened + currentPart
        }
    }
}

data class RelativeContour(
    val positions: List<RelativePosition>,
)

data class RelativePosition(
    val courseRadians: Double,
    val distanceMeters: Double,
)