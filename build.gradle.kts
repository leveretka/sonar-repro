// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.sonar) apply true
}

sonar {
    properties {
        property("sonar.projectKey", "contact-sync")
        property("sonar.host.url", "https://sonarqube.acme.com/")
        property("sonar.token", System.getenv("SONAR_TOKEN"))
    }
}