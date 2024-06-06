package maps

import MapsConfig
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import com.yandex.runtime.image.ImageProvider
import maps.bindings.GeoPlacemarkImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot
import worlds.composeapp.generated.resources.map_touch_area

@Composable
actual fun MapsConfig.dotImage(): GeoPlacemarkImage {
    return provider(Res.drawable.map_dot)
}

@Composable
actual fun MapsConfig.touchAreaImage(): GeoPlacemarkImage {
    return provider(Res.drawable.map_touch_area)
}

@Composable
private fun provider(resource: DrawableResource):  GeoPlacemarkImage {
    return ImageProvider.fromBitmap(imageResource(resource).asAndroidBitmap())
}