plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.mokoResources)
}
buildscript {
    dependencies {
        classpath(libs.build.konfig)
        classpath(libs.moko.resources.generator)
    }
}