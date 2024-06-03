package maps.bindings

import cocoapods.YandexMapsMobile.YMKAnimation
import cocoapods.YandexMapsMobile.YMKAnimationType
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCameraUpdateReason
import cocoapods.YandexMapsMobile.YMKMap
import cocoapods.YandexMapsMobile.YMKMapCameraListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKMapWindow
import kotlinx.cinterop.COpaque
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.objcPtr
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_getAssociatedObject
import platform.objc.objc_setAssociatedObject
import kotlin.native.ref.WeakReference

actual class GeoMap(private val mapView: YMKMapView) {
    private inline fun <R> withMap(block: YMKMap.() -> R): R {
        return withMapWindow { map.block() }
    }

    private inline fun <R> withMapWindow(block: YMKMapWindow.() -> R): R {
        return mapView.mapWindow?.let(block)!!
    }

    actual fun screenToWorld(screenPoint: GeoScreenPoint): GeoPoint? {
        return withMapWindow { screenToWorldWithScreenPoint(screenPoint) }
    }

    actual fun addCameraListener(listener: GeoMapCameraListener) {
        withMap { addCameraListenerWithCameraListener(CameraListenerWrapper(listener)) }
    }

    actual fun removeCameraListener(listener: GeoMapCameraListener) {
        withMap { removeCameraListenerWithCameraListener(CameraListenerWrapper(listener)) }
    }

    actual fun addCollection(): GeoMapObjectCollection {
        return withMap { GeoMapObjectCollectionImpl(mapObjects.addCollection()) }
    }

    actual fun removeCollection(collection: GeoMapObjectCollection) {
        withMap { mapObjects.removeWithMapObject((collection as GeoMapObjectCollectionImpl).impl) }
    }

    actual fun moveCamera(position: GeoCameraPosition, animated: Boolean) {
        withMap {
            if (animated) {
                moveWithCameraPosition(
                    position,
                    YMKAnimation.animationWithType(YMKAnimationType.YMKAnimationTypeLinear, 300f),
                    null,
                )
            } else {
                moveWithCameraPosition(position)
            }
        }
    }

    actual fun cameraPosition(): GeoCameraPosition {
        return withMap { cameraPosition() }
    }
}

@Suppress("FunctionName")
private fun CameraListenerWrapper(impl: GeoMapCameraListener): YMKCameraListenerWrapper {
    @Suppress("RedundantCompanionReference")
    val pointerToCompanionObject =
        interpretCPointer<COpaque>(YMKCameraListenerWrapper.ForNativePtr.objcPtr())
    val value = autoreleasepool {
        objc_getAssociatedObject(impl, pointerToCompanionObject)
    }

    if (value != null) {
        return value as YMKCameraListenerWrapper
    }

    val result = YMKCameraListenerWrapper(impl, Unit)
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

private class YMKCameraListenerWrapper(
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
