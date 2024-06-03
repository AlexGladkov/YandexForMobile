package maps

import maps.bindings.Coordinates
import maps.bindings.GeoUtils
import kotlin.math.PI

fun GeoUtils.calculateRelativeContour(
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

fun GeoUtils.degreesToRadians(degrees: Double): Double {
    return (degrees / 180) * PI
}