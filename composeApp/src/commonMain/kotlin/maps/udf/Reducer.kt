package maps.udf

import maps.bindings.Coordinates
import maps.bindings.GeoUtils.courseDegrees
import maps.bindings.GeoUtils.distanceMeters
import kotlin.math.PI

fun reduce(state: MapsState, event: MapsEvent): MapsState {
    return with(state) {
        state.copy(
            screen = screen.reduce(event),
        )
    }
}

private fun MapScreen.reduce(event: MapsEvent): MapScreen {
    return when (this) {
        is MapScreen.ExpectFun -> reduceExpectFun(event)
        is MapScreen.ActualFun -> reduceActualFun(event)
    }
}

private fun MapScreen.ActualFun.reduceActualFun(event: MapsEvent): MapScreen {
    return when (event) {
        is GoToExpectFun -> MapScreen.ExpectFun()
        is UpdateReferencePoint -> copy(referencePoint = event.point)
        else -> this
    }
}

private fun MapScreen.ExpectFun.reduceExpectFun(event: MapsEvent): MapScreen {
    return when (event) {
        is GoToActualFun -> {
            val referencePoint = event.contourPoints.calculateReferencePoint()
                ?: event.currentMapCenter
            val contour = calculateRelativeContour(
                referencePoint,
                event.contourPoints,
            )
            MapScreen.ActualFun(
                referencePoint = referencePoint,
                contour = contour,
            )
        }

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

private fun List<Coordinates>.calculateReferencePoint(): Coordinates? {
    if (isEmpty()) return null

    return Coordinates(
        lat = sumOf { it.lat } / size,
        lon = sumOf { it.lon } / size,
    )
}

private fun calculateRelativeContour(
    referencePoint: Coordinates,
    contourPoints: List<Coordinates>,
): RelativeContour {
    val mapkitReferencePoint = referencePoint.cachePoint

    val relativePositions = contourPoints.map { point ->
        val mapkitPoint = point.cachePoint

        RelativePosition(
            courseRadians = degreesToRadians(courseDegrees(mapkitReferencePoint, mapkitPoint)),
            distanceMeters = distanceMeters(mapkitReferencePoint, mapkitPoint),
        )
    }

    return RelativeContour(relativePositions)
}

private fun degreesToRadians(degrees: Double): Double {
    return (degrees / 180) * PI
}