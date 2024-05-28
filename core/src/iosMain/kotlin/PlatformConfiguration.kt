actual class PlatformConfiguration(
    val openFlutterPro: () -> Unit
) {
    actual fun openFlutterModule(key: String) = openFlutterPro()
}