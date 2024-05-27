package maps.bindings

import cocoapods.YandexMapsMobile.YMKMapObject
import cocoapods.YandexMapsMobile.YMKMapObjectCollection
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject

actual interface GeoMapObject {
    actual fun setDraggable(draggable: Boolean)
    actual fun setDragListener(listener: GeoMapObjectDragListener?)
    actual fun setVisible(visible: Boolean)
}

private open class GeoMapObjectImpl(private val impl: YMKMapObject) : GeoMapObject {

    final override fun setDraggable(draggable: Boolean) {
        impl.setDraggable(draggable)
    }

    final override fun setDragListener(listener: GeoMapObjectDragListener?) {
        impl.setDragListenerWithDragListener(listener?.let(::MapObjectDragListenerWrapper))
    }

    final override fun setVisible(visible: Boolean) {
        impl.setVisible(visible)
    }
}

actual interface GeoMapObjectCollection {
    actual fun addPlacemark(): GeoPlacemark
}

class GeoMapObjectCollectionImpl(val impl: YMKMapObjectCollection) : GeoMapObjectCollection {
    override fun addPlacemark(): GeoPlacemark = GeoPlacemarkImpl(impl.addPlacemark())
}

actual interface GeoPlacemark : GeoMapObject {
    actual fun getGeometry(): GeoPoint
    actual fun setGeometry(point: GeoPoint)
    actual fun setIcon(image: GeoPlacemarkImage)
}

private class GeoPlacemarkImpl(val impl: YMKPlacemarkMapObject) : GeoPlacemark,
    GeoMapObjectImpl(impl) {
    override fun getGeometry(): GeoPoint {
        return impl.geometry()
    }

    override fun setGeometry(point: GeoPoint) {
        impl.setGeometry(point)
    }

    override fun setIcon(image: GeoPlacemarkImage) {
        impl.setIconWithImage(image.wrapped)
    }
}


fun YMKMapObject.wrap(): GeoMapObject {
    return when (this) {
        is YMKPlacemarkMapObject -> GeoPlacemarkImpl(this)
        else -> GeoMapObjectImpl(this)
    }
}