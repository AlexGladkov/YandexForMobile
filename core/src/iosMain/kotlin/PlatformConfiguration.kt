actual class PlatformConfiguration(
    val openFlutterPro: () -> Unit,
    val openBrowserDivKit: () -> Unit,
    val mapsConfig: MapsConfig,
) {
    actual fun openFlutterModule(key: String) = openFlutterPro()

    actual fun openBrowserDivKitScreen() = openBrowserDivKit()
    actual fun mapsConfig(): MapsConfig = mapsConfig


}