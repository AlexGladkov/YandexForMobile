package maps

data class MapsState(val screen: MapsScreen = MapsScreen.Fun.ExpectFun())

sealed interface MapsScreen {
    data object Boring : MapsScreen

    sealed interface Fun : MapsScreen {
        data class ExpectFun(
            val contour: EditableContour = EditableContour(),
        ) : Fun

        data class ActualFun(
            val referencePoint: Coordinates,
            val contour: RelativeContour,
        ) : Fun
    }
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