@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("base-android-plugin")
    id("hilt-plugin")
}

android {
    namespace = "com.example.core"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.appcompat)
    implementation(libs.material)
    api(libs.coil)
    debugApi(libs.ui.tooling)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.espresso)
}
