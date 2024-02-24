import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.build.konfig.get().toString())
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget() {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
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
            implementation(libs.bundles.ktor.android)
        }

        iosMain.dependencies {
            implementation(libs.bundles.ktor.ios)
        }
    }
}

android {
    namespace = "com.example.core_network"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
val modulePackageName = "com.example.core"

buildkonfig {
    packageName = modulePackageName
    exposeObjectWithName = "CoreBuildKonfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.BOOLEAN, "IS_DEBUG", "true")

        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "https://www.newsapi.ai/api/v1")

        buildConfigField(FieldSpec.Type.STRING, "API_KEY", "")
    }

    defaultConfigs("debug") {
        buildConfigField(FieldSpec.Type.BOOLEAN, "IS_DEBUG", "true")
    }

    defaultConfigs("release") {
        buildConfigField(FieldSpec.Type.BOOLEAN, "IS_DEBUG", "false")
    }
}