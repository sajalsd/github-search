@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("base-android-plugin")
    id("hilt-plugin")
}

android {
    namespace = "com.example.network"
}

dependencies {
    implementation(project(":core"))
    implementation(libs.core.ktx)
    implementation(libs.bundles.okhttp)
    api(libs.retrofit)
    api(libs.bundles.moshi)

    kapt(libs.moshiCodegen)
    testImplementation(libs.bundles.test)
}
