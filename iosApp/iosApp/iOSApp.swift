import SwiftUI
import Flutter
import FlutterPluginRegistrant


class FlutterDependencies: ObservableObject {
 let flutterEngine = FlutterEngine(name: "FlutterEngine")
    
 init() {
   // Runs the default Dart entrypoint with a default Flutter route.
   flutterEngine.run()
   // Connects plugins with iOS platform code to this app.
   GeneratedPluginRegistrant.register(with: self.flutterEngine);
 }
}

@main
struct iOSApp: App {
    @StateObject var flutterDependencies = FlutterDependencies()
	var body: some Scene {
		WindowGroup {
            ContentView().environmentObject(flutterDependencies)
		}
	}
}
