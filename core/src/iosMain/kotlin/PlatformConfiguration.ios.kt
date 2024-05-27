import platform.UIKit.UIImage

actual class MapsConfig(
    val dotImage: UIImage,
    val touchAreaImage: UIImage,
    actual val log: (String) -> Unit,
)