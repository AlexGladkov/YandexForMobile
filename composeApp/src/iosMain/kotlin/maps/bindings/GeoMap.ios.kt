package maps.bindings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asSkiaBitmap
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCameraUpdateReason
import cocoapods.YandexMapsMobile.YMKMap
import cocoapods.YandexMapsMobile.YMKMapCameraListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapObjectCollection
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKMapWindow
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import cocoapods.YandexMapsMobile.YMKScreenPoint
import coil3.asCoilImage
import kotlinx.cinterop.COpaque
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.objcPtr
import kotlinx.cinterop.usePinned
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import platform.Foundation.NSCoder
import platform.Foundation.NSData
import platform.Foundation.NSMutableData
import platform.Foundation.appendBytes
import platform.UIKit.UIImage
import platform.darwin.NSObject
import platform.objc.OBJC_ASSOCIATION_RETAIN
import platform.objc.objc_getAssociatedObject
import platform.objc.objc_setAssociatedObject
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot
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

    actual fun addCollection(): GeoMapObjectCollection {
        return withMap { GeoMapObjectCollectionImpl(mapObjects.addCollection()) }
    }

    actual fun removeCollection(collection: GeoMapObjectCollection) {
        withMap { mapObjects.removeWithMapObject((collection as GeoMapObjectCollectionImpl).impl) }
    }

    private inline fun <R> withMap(block: YMKMap.() -> R): R {
        return withMapWindow { map.block() }
    }

    private inline fun <R> withMapWindow(block: YMKMapWindow.() -> R): R {
        return mapView.mapWindow?.let(block)!!
    }
}

private fun CameraListenerWrapper(impl: GeoMapCameraListener): CameraListenerWrapper {
    @Suppress("RedundantCompanionReference")
    val pointerToCompanionObject =
        interpretCPointer<COpaque>(CameraListenerWrapper.ForNativePtr.objcPtr())
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

actual fun makeGeoScreenPoint(x: Float, y: Float): GeoScreenPoint {
    return YMKScreenPoint.screenPointWithX(x = x, y = y)
}

actual interface GeoMapObjectCollection

class GeoMapObjectCollectionImpl(val impl: YMKMapObjectCollection) : GeoMapObjectCollection

actual interface GeoPlacemark {
    actual fun setGeometry(point: MapkitPoint)
    actual fun setIcon(image: GeoPlacemarkImage)
}

fun IntArray.toNSData(): NSData {
    val data = NSMutableData()
    usePinned { pinned ->
        for (index in indices) {
            data.appendBytes(pinned.addressOf(index), 4u)
        }
    }
    return data
}


class GeoPlacemarkImpl(val impl: YMKPlacemarkMapObject) : GeoPlacemark {

    override fun setGeometry(point: MapkitPoint) {
        impl.setGeometry(point)
    }

    override fun setIcon(image: GeoPlacemarkImage) {
        impl.setIconWithImage(image.wrapped)
    }
}

actual abstract class GeoPlacemarkImage {
    abstract val wrapped: UIImage
}

class GeoPlacemarkImageImpl(override val wrapped: UIImage) : GeoPlacemarkImage()

@Composable
actual fun DrawableResource.readAsGeoPlacemarkImage(): GeoPlacemarkImage {
    return imageResource(Res.drawable.map_dot).run {
        val storage = IntArray(width * height)
        readPixels(storage)
        GeoPlacemarkImageImpl(UIImage(storage.toNSData()))
    }
}