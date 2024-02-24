plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sql.delight)
    alias(libs.plugins.ktlint)
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
            isStatic = false
            linkerOpts.add("-lsqlite3")
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
            //sql-delight-coroutines
            implementation(libs.bundles.sqldelight.common)
            // store
            implementation(libs.store)
            // multiplatform storage settings
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.couroutine)
            // Kotlin DateTime
            implementation(libs.kotlin.datetime)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(compose.preview)
            // coil
            implementation(libs.coil)
            //sql-delight
            implementation(libs.android.sqldelight)
        }

        iosMain.dependencies {
            //sql-delight
            implementation(libs.ios.sqldelight)
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

sqldelight {
    databases {
        create("NewsDatabase") {
            packageName.set("com.example.news_kmp.shared")
        }
        linkSqlite = true
    }
}
