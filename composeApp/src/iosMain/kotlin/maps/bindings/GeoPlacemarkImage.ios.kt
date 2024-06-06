package maps.bindings

import platform.UIKit.UIImage

actual abstract class GeoPlacemarkImage {
    abstract val wrapped: UIImage
}

class GeoPlacemarkImageImpl(override val wrapped: UIImage) : GeoPlacemarkImage()

