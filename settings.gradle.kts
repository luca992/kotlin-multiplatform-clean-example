import de.fayard.dependencies.bootstrapRefreshVersionsAndDependencies

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}


buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard:dependencies:0.5.7")
}

bootstrapRefreshVersionsAndDependencies()


rootProject.name = "multiplatform-template"
include("app")
include("data")
include("domain")
include("device")

enableFeaturePreview("GRADLE_METADATA")
