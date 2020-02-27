pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

rootProject.name = "multiplatform-template"
include("app")
include("data")
include("domain")
include("device")

enableFeaturePreview("GRADLE_METADATA")
