plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            // Shared Core Base Module
            api(project(":shared:core"))

            api(project(":shared:core-ui"))

            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(compose.runtime)
            //Ktor
            implementation(libs.bundles.ktor.common)
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            // Kermit -- Logs
            implementation(libs.touchlab.kermit)
            // Koin
            implementation(libs.koin.core)
            // moko-common
            implementation(libs.bundles.moko.common)
            // kamelImage Loading
            implementation(libs.kamel)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(compose.preview)
            // coil
            implementation(libs.coil)
        }
    }
}

android {
    namespace = "com.example.news_kmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
