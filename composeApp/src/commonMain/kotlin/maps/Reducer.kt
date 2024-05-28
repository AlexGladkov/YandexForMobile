package maps

fun reduce(state: MapsState, event: MapsEvent): MapsState {
    return with(state) {
        state.copy(
            screen = screen.reduce(event)
        )
    }
}

private fun MapsScreen.reduce(event: MapsEvent): MapsScreen {
    return when (this) {
        is MapsScreen.Boring -> reduceBoring(event)
        is MapsScreen.Fun.ExpectFun -> reduceExpectFun(event)
        is MapsScreen.Fun.ActualFun -> reduceActualFun(event)
    }
}

private fun MapsScreen.Fun.ActualFun.reduceActualFun(event: MapsEvent): MapsScreen {
    return when (event) {
        is GoToExpectFun -> MapsScreen.Fun.ExpectFun()
        is UpdateReferencePoint -> copy(referencePoint = event.point)
        else -> this
    }
}

private fun MapsScreen.Boring.reduceBoring(event: MapsEvent): MapsScreen = this

private fun MapsScreen.Fun.ExpectFun.reduceExpectFun(event: MapsEvent): MapsScreen {
    return when (event) {
        is GoToActualFun -> MapsScreen.Fun.ActualFun(
            referencePoint = event.currentMapCenter,
            contour = event.contour,
        )

        else -> copy(
            contour = contour.reduce(event),
        )
    }
}

private fun EditableContour.reduce(event: MapsEvent): EditableContour {
    return when (event) {
        is AddPoint -> copy(points = points + event.point)
        else -> this
    }
}
