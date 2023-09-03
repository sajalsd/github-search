plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}
dependencies{
    implementation(libs.findLibrary("hilt").get())
    kapt(libs.findLibrary("hiltKapt").get())
}

