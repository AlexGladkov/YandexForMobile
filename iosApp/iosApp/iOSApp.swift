import SwiftUI
import Flutter
import FlutterPluginRegistrant
import YandexMapsMobile


class FlutterDependencies: ObservableObject {
 let flutterEngine = FlutterEngine(name: "FlutterEngine")
    
 init() {
   // Runs the default Dart entrypoint with a default Flutter route.
   flutterEngine.run()
   // Connects plugins with iOS platform code to this app.
   GeneratedPluginRegistrant.register(with: self.flutterEngine);
 }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        YMKMapKit.setApiKey("79d2937d-a59b-4cd0-8bda-8346ceb374a2")
        YMKMapKit.sharedInstance().onStart()
        return true
    }
}


@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    @StateObject var flutterDependencies = FlutterDependencies()
	var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(flutterDependencies)
        }
	}
    
    
}
