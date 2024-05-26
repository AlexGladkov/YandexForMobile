import UIKit
import SwiftUI
import ComposeApp
import Flutter

struct ComposeView: UIViewControllerRepresentable {
    var flutterDependencies: FlutterDependencies

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(callback: showFlutter)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
<<<<<<< HEAD
}

struct ContentView: View {
    @EnvironmentObject var flutterDependencies: FlutterDependencies

    var body: some View {
        ComposeView()
        .ignoresSafeArea(edges: .all)
        .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }

=======
    
>>>>>>> 13f0b3b (Feature: integrete ios Flutter module in app)
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
}


struct ContentView: View {
    @EnvironmentObject var flutterDependencies: FlutterDependencies
    
    var body: some View {
        ComposeView(flutterDependencies: flutterDependencies).ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
    
}
