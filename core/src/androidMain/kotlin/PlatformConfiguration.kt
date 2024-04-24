import android.content.Context
import android.content.Intent

actual class PlatformConfiguration(
    val applicationContext: Context, val openFlutterPro: () -> Unit
) {
    actual fun openFlutterModule(key: String) = openFlutterPro()
}