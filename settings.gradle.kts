import de.fayard.dependencies.bootstrapRefreshVersionsAndDependencies

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}


buildscript {
    val versionsProperties : java.util.Properties by lazy {
        val versions = java.util.Properties()
        val localProperties: File = File(rootProject.projectDir.absoluteFile.absolutePath+"/versions.properties")
        if (localProperties.exists()) {
            localProperties.inputStream().use { versions.load(it) }
        }
        versions
    }
    val kotlinVersion = "${versionsProperties["version.kotlin"]}"


    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
    dependencies{
        classpath("de.fayard:dependencies:0.5.8")
        classpath ("com.android.tools.build:gradle:4.1.0-alpha08")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
        classpath("com.squareup.sqldelight:gradle-plugin:${versionsProperties["version.sqldelight"]}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${versionsProperties["version.androidx.navigation"]}")
        classpath("com.adarshr:gradle-test-logger-plugin:2.0.0")
    }
}

bootstrapRefreshVersionsAndDependencies()


rootProject.name = "multiplatform-template"
include("app")
include("data")
include("domain")
include("device")

enableFeaturePreview("GRADLE_METADATA")
