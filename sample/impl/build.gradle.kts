plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sample.api)
        }
    }
}

android {
    namespace = "dev.yandex.sample.impl"
}