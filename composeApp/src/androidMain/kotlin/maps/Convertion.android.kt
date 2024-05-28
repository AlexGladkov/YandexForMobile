package maps

import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import kotlin.math.PI

actual fun calculateRelativeContour(
    referencePoint: Coordinates,
    contourPoints: List<Coordinates>,
): RelativeContour {

    val mapkitReferencePoint = referencePoint.toMapkitPoint()

    val relativePositions = contourPoints.map { point ->
        val mapkitPoint = point.toMapkitPoint()

        RelativePosition(
            courseRadians = (Geo.course(mapkitReferencePoint, mapkitPoint) / 180.0) * PI,
            distanceMeters = Geo.distance(mapkitReferencePoint, mapkitPoint),
        )
    }

    return RelativeContour(relativePositions)
}

fun Coordinates.toMapkitPoint() = Point(lat, lon)