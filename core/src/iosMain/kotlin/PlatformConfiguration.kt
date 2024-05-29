actual class PlatformConfiguration(
    val openFlutterPro: () -> Unit,
    val openBrowserDivKit: () -> Unit,
) {
    actual fun openFlutterModule(key: String) = openFlutterPro()

    actual fun openBrowserDivKitScreen() = openBrowserDivKit()
}