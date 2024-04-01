plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kviewmodel.compose)
            implementation(libs.kviewmodel.core)
            implementation(libs.kviewmodel.odyssey)
            
            implementation(libs.odyssey.compose)
            implementation(libs.odyssey.core)
        }
    }
}

android {
    namespace = "dev.yandex.sample.impl"
}