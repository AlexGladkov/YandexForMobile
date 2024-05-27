package tech.mobiledeveloper.worlds

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class WorldsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("79d2937d-a59b-4cd0-8bda-8346ceb374a2")
    }
}