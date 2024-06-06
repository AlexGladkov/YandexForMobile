package maps.bindings

import cocoapods.YandexMapsMobile.YMKMapObject
import cocoapods.YandexMapsMobile.YMKMapObjectDragListenerProtocol
import cocoapods.YandexMapsMobile.YMKPoint
import kotlinx.cinterop.COpaque
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.objcPtr
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_getAssociatedObject
import platform.objc.objc_setAssociatedObject
import kotlin.native.ref.WeakReference

actual interface GeoMapObjectDragListener {
    actual fun onMapObjectDragStart(mapObject: GeoMapObject)
    actual fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint)
    actual fun onMapObjectDragEnd(mapObject: GeoMapObject)
}


class MapObjectDragListenerWrapper(
    impl: GeoMapObjectDragListener,
    @Suppress("UNUSED_PARAMETER") tag: Unit,
) : NSObject(), YMKMapObjectDragListenerProtocol {
    private val impl = WeakReference(impl)

    override fun onMapObjectDragStartWithMapObject(mapObject: YMKMapObject) {
        impl.get()?.onMapObjectDragStart(mapObject.wrap())
    }

    override fun onMapObjectDragWithMapObject(mapObject: YMKMapObject, point: YMKPoint) {
        impl.get()?.onMapObjectDrag(mapObject.wrap(), point)
    }

    override fun onMapObjectDragEndWithMapObject(mapObject: YMKMapObject) {
        impl.get()?.onMapObjectDragEnd(mapObject.wrap())
    }

    internal companion object ForNativePtr
}

fun MapObjectDragListenerWrapper(impl: GeoMapObjectDragListener): MapObjectDragListenerWrapper {
    @Suppress("RedundantCompanionReference")
    val pointerToCompanionObject =
        interpretCPointer<COpaque>(MapObjectDragListenerWrapper.ForNativePtr.objcPtr())
    val value = autoreleasepool { objc_getAssociatedObject(impl, pointerToCompanionObject) }

    if (value != null) {
        return value as MapObjectDragListenerWrapper
    }

    val result = MapObjectDragListenerWrapper(impl, Unit)
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

