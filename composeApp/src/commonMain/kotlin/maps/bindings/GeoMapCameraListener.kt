package maps.bindings

import maps.CameraMove

interface GeoMapCameraListener {
    fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean)
}

class CameraListenerLambdaWrapper(private val onCameraMoved: ((CameraMove) -> Unit)): GeoMapCameraListener {
    override fun onCameraPositionChanged(position: GeoCameraPosition, finished: Boolean) {
        onCameraMoved(CameraMove(position, finished))
    }
}