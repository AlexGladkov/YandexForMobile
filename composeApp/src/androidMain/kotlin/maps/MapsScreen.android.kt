package maps

import MapsConfig
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import com.yandex.runtime.image.ImageProvider
import maps.bindings.GeoPlacemarkImage
import org.jetbrains.compose.resources.imageResource
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_dot

@Composable
actual fun MapsConfig.dotConfig(): GeoPlacemarkImage {
    return ImageProvider.fromBitmap(imageResource(Res.drawable.map_dot).asAndroidBitmap())
}