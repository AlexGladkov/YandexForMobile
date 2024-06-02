package maps

import MapsConfig
import androidx.compose.runtime.Composable
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoPlacemarkImageImpl

@Composable
actual fun MapsConfig.dotConfig(): GeoPlacemarkImage {
    return GeoPlacemarkImageImpl(dotImage)
}