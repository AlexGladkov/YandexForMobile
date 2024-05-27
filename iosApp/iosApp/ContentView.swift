import UIKit
import SwiftUI
import ComposeApp
import Flutter

struct ComposeView: UIViewControllerRepresentable {
    var flutterDependencies: FlutterDependencies
    var showFlutter: () -> Void
    var showBrowserDivKitScreen: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            openFlutterPro: showFlutter,
            openBrowserDivKit: showBrowserDivKitScreen,
            mapsConfig: CoreMapsConfig(
                dotImage: UIImage(named: "map_dot")!,
                touchAreaImage: UIImage(named: "map_touch_area")!,
                log: log
            )
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
    
    func log(message: String) {
        NSLog(message)
    }
}

struct ContentView: View {
    @EnvironmentObject var flutterDependencies: FlutterDependencies

    var body: some View {
        ComposeView(
            flutterDependencies: flutterDependencies,
            showFlutter: showFlutter,
            showBrowserDivKitScreen: showBrowserDivKitScreen
        )
            .ignoresSafeArea(edges: .all)
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }

    func showFlutter() {
        guard
            let windowScene = UIApplication.shared.connectedScenes
                .first(where: { $0.activationState == .foregroundActive && $0 is UIWindowScene }) as? UIWindowScene,
            let window = windowScene.windows.first(where: \.isKeyWindow),
            let rootViewController = window.rootViewController
        else { return }

        // Create the FlutterViewController.
        let flutterViewController = FlutterViewController(
            engine: flutterDependencies.flutterEngine,
            nibName: nil,
            bundle: nil)
        flutterViewController.modalPresentationStyle = .overCurrentContext
        flutterViewController.isViewOpaque = false

        rootViewController.present(flutterViewController, animated: true)
    }

    func showBrowserDivKitScreen() {
        // TODO
    }
}
