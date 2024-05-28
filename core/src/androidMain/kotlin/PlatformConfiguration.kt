import android.content.Context

actual class PlatformConfiguration(
    val openFlutterModuleCallback: (key: String) -> Unit
) {
    actual fun openFlutterModule(key: String) = openFlutterModuleCallback(key)
}