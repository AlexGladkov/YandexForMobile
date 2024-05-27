expect class PlatformConfiguration {
    fun openFlutterModule(key: String)

    fun openBrowserDivKitScreen()

    fun mapsConfig(): MapsConfig
}

expect class MapsConfig {
    val log: (String) -> Unit
}