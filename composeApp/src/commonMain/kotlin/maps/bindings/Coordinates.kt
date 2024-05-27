package maps.bindings

class Coordinates(val cachePoint: GeoPoint) {
    constructor(lat: Double, lon: Double) : this(makeGeoPoint(lat = lat, lon = lon))

    val lat by cachePoint::lat
    val lon by cachePoint::lon

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinates) return false

        return other.lat == lat && other.lon == lon
    }

    override fun hashCode(): Int {
        return lat.hash() * 31 + lon.hash()
    }

    private fun Double.hash(): Int {
        val bits = toBits()
        return (bits shr 32).toInt() xor bits.toInt()
    }
}