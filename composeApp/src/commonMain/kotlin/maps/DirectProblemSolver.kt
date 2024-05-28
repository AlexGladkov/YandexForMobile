package maps

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

object DirectProblemSolver {
    private const val a = 6378137.0 // экваториальный радиус
    private const val e2 = 0.00669437999014 // эксцентриситет в квадрате
    private val f = 1 - sqrt(1.0 - e2) // сжатие
    private val b = (1 - f) * a // полярный радиус
    private const val eps = 1e-10 // Точность вычислений

    fun solveDirectProblem(
        startPoint: Coordinates,
        courseRadians: Double,
        distance: Double
    ): Coordinates {
        val directionLat = cos(courseRadians)
        val directionLon = sin(courseRadians)

        val actualStartLat = cutLat(startPoint.lat)

        val lat = actualStartLat * PI / 180.0
        val long = startPoint.lon * PI / 180
        val l = sqrt(directionLat * directionLat + directionLon * directionLon)
        val cosAzimuth = directionLat / l
        val sinAzimuth = directionLon / l
        val tanU1 = (1 - f) * tan(lat)
        val cosU1 = sign(cos(lat)) * sqrt(1 / (1 + tanU1 * tanU1))
        val sinU1 = sqrt(1 - cosU1 * cosU1) * sign(tanU1)
        val sigma1 = atan2(tanU1, cosAzimuth)
        val sinAlpha = cosU1 * sinAzimuth
        val cos2Alpha = (1 - sinAlpha) * (1 + sinAlpha)
        val u2 = cos2Alpha * (a * a / (b * b) - 1)
        val A = 1 + u2 * (4096 + u2 * (-768 + u2 * (320 - 175 * u2))) / 16384
        val B = u2 * (256 + u2 * (-128 + u2 * (74 - 47 * u2))) / 1024

        fun getPoint(t: Double): Coordinates {
            val d = distance * t
            var sigma = d / (b * A)
            val sigmam = sigma1 + sigma / 2
            val cos2sigmam = cos(2 * sigmam)
            var i = 0
            var prev = 0.0
            var change: Double

            do {
                val dsigma =
                    B * sin(sigma) * (cos2sigmam + 0.25 * B * (cos(sigma) * (-1 + 2 * cos2sigmam * cos2sigmam) - 1.0 / 6.0 * B * cos2sigmam *
                            (-3.0 + 4.0 * sin(sigma) * sin(sigma)) * (-3.0 + 4.0 * cos2sigmam * cos2sigmam)))
                change = dsigma - prev
                prev = dsigma
                sigma = d / (b * A) + dsigma
                i++
            } while (abs(change) > eps && i < 10)

            val tmp = sinU1 * sin(sigma) - cosU1 * cos(sigma) * cosAzimuth
            val lat2 = atan2(
                sinU1 * cos(sigma) + cosU1 * sin(sigma) * cosAzimuth,
                (1 - f) * sqrt(
                    sinAlpha * sinAlpha + tmp * tmp
                )
            )

            val lambda = atan2(
                sin(sigma) * sinAzimuth,
                cosU1 * cos(sigma) - sinU1 * sin(sigma) * cosAzimuth
            )
            val C = (f / 16.0) * cos2Alpha * (4.0 + f * (4.0 - 3.0 * cos2Alpha))
            val dlong = lambda - (1 - C) * f * sinAlpha * (
                    sigma + C * sin(sigma) * (
                            cos2sigmam + C * cos(sigma) * (-1.0 + 2.0 * cos2sigmam * cos2sigmam)
                            )
                    )
            return Coordinates(
                lat = lat2 * 180.0 / PI,
                lon = cycleRestrict((long + dlong) * 180.0 / PI, -180.0, 180.0),
            )
        }

        return getPoint(1.0)
    }

    private fun cycleRestrict(value: Double, min: Double, max: Double): Double {
        return value - floor((value - min) / (max - min)) * (max - min);
    }

    private fun cutLat(lat: Double) = lat.coerceIn(-89.999, 89.999)

}