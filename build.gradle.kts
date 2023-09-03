@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(libs.plugins.android.library.get().pluginId) apply false
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.kotlin.android.get().pluginId) apply false
    id(libs.plugins.ktlint.get().pluginId) version libs.versions.ktlint.get() apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")


    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

subprojects {
    apply("${rootProject.rootDir}/gradle/task.gradle")
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}
