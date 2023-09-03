@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("base-android-plugin")
    id("hilt-plugin")
}

android {
    namespace = "com.example.search"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(libs.core.ktx)
    implementation(libs.paging3)
    implementation(libs.paging3Compose)
    implementation(libs.bundles.compose)
    implementation(libs.hiltNavigation)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.espresso)
}
