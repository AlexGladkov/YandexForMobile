package maps.bindings

interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

data class CameraMove(
    val position: GeoCameraPosition,
    val finished: Boolean
)

class CameraListenerLambdaWrapper(private val onCameraMoved: ((CameraMove) -> Unit)): GeoMapCameraListener {
    override fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean) {
        onCameraMoved(CameraMove(position, finished))
    }
}