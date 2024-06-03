package maps

import MapsConfig
import androidx.compose.runtime.Composable
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoPlacemarkImageImpl

@Composable
actual fun MapsConfig.dotImage(): GeoPlacemarkImage {
    return GeoPlacemarkImageImpl(dotImage)
}

@Composable
actual fun MapsConfig.touchAreaImage(): GeoPlacemarkImage {
    return GeoPlacemarkImageImpl(touchAreaImage)
}