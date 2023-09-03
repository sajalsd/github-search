// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.2" apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply("${rootProject.rootDir}/gradle/task.gradle")
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}