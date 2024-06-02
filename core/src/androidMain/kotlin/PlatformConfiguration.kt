actual class PlatformConfiguration(
    val openFlutterModuleCallback: (key: String) -> Unit,
    val openBrowserDivKit: () -> Unit,
    val mapsConfig: MapsConfig = MapsConfig(),
) {
    actual fun openFlutterModule(key: String) = openFlutterModuleCallback(key)

    actual fun openBrowserDivKitScreen() = openBrowserDivKit()
    actual fun mapsConfig(): MapsConfig = mapsConfig
}