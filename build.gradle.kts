// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.15.0")
        force("com.squareup:javawriter:2.5.1")
    }
}