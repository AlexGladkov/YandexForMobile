actual class PlatformConfiguration(
    val openFlutterModuleCallback: (key: String) -> Unit,
    val openBrowserDivKit: () -> Unit
) {
    actual fun openFlutterModule(key: String) = openFlutterModuleCallback(key)

    actual fun openBrowserDivKitScreen() = openBrowserDivKit()
}