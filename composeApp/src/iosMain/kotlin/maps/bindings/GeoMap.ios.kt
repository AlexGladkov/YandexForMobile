package maps.bindings

import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCameraUpdateReason
import cocoapods.YandexMapsMobile.YMKMap
import cocoapods.YandexMapsMobile.YMKMapCameraListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKMapWindow
import cocoapods.YandexMapsMobile.YMKScreenPoint
import kotlinx.cinterop.COpaque
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.objcPtr
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_getAssociatedObject
import platform.objc.objc_setAssociatedObject
import kotlin.native.ref.WeakReference

actual typealias GeoScreenPoint = YMKScreenPoint
actual typealias GeoCameraPosition = YMKCameraPosition

actual class GeoMap(private val mapView: YMKMapView) {
    actual fun screenToWorld(screenPoint: GeoScreenPoint): MapkitPoint? {
        return withMapWindow { screenToWorldWithScreenPoint(screenPoint) }
    }

    actual fun addCameraListener(listener: GeoMapCameraListener) {
        withMap { addCameraListenerWithCameraListener(CameraListenerWrapper(listener)) }
    }

    actual fun removeCameraListener(listener: GeoMapCameraListener) {
        withMap { removeCameraListenerWithCameraListener(CameraListenerWrapper(listener)) }
    }

    private inline fun <R> withMap(block: YMKMap.() -> R): R? {
        return withMapWindow { map.block() }
    }

    private inline fun <R> withMapWindow(block: YMKMapWindow.() -> R): R? {
        return mapView.mapWindow?.let(block)
    }
}

private fun CameraListenerWrapper(impl: GeoMapCameraListener): CameraListenerWrapper {
    @Suppress("RedundantCompanionReference")
    val pointerToCompanionObject = interpretCPointer<COpaque>(CameraListenerWrapper.ForNativePtr.objcPtr())
    val value = autoreleasepool {
        objc_getAssociatedObject(impl, pointerToCompanionObject)
    }

    if (value != null) {
        return value as CameraListenerWrapper
    }

    val result = CameraListenerWrapper(impl, Unit)
    autoreleasepool {
        objc_setAssociatedObject(
            impl,
            pointerToCompanionObject,
            result,
            OBJC_ASSOCIATION_RETAIN,
        )
    }
    return result
}

private class CameraListenerWrapper(
    impl: GeoMapCameraListener,
    @Suppress("UNUSED_PARAMETER") tag: Unit
) : NSObject(), YMKMapCameraListenerProtocol {
    private val impl = WeakReference(impl)

    override fun onCameraPositionChangedWithMap(
        map: YMKMap,
        cameraPosition: YMKCameraPosition,
        cameraUpdateReason: YMKCameraUpdateReason,
        finished: Boolean,
    ) {
        impl.get()?.onCameraPositionChanged(
            cameraPosition,
            finished,
        )
    }

    companion object ForNativePtr
}