import de.fayard.versions.bootstrapRefreshVersions
import de.fayard.dependencies.DependenciesPlugin

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies.classpath("de.fayard:dependencies:0.5.6")
}

bootstrapRefreshVersions(DependenciesPlugin.artifactVersionKeyRules)

plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "multiplatform-template"
include("app")
include("data")
include("domain")
include("device")

enableFeaturePreview("GRADLE_METADATA")
