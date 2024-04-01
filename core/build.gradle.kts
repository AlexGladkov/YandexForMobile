plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kodein)
        }
    }
}

android {
    namespace = "dev.yandex.core"
}