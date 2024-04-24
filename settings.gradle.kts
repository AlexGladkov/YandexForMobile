rootProject.name = "Worlds"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://storage.googleapis.com/download.flutter.io")
        maven(url = "modules/yandex_pro_landing/build/host/outputs/repo")
    }
}

include(":composeApp", "sample", "core")