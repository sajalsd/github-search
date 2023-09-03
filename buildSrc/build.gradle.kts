@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}


dependencies {
    implementation(gradleApi())
    implementation(libs.gradle)
    implementation(libs.gradle.api)
    implementation(libs.kotlin.gradle.plugin)
}