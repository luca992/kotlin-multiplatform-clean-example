import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

var buildForNative = false

object BuildPlugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinMultiplatform = "kotlin-multiplatform"
    const val kotlinCocoapods = "org.jetbrains.kotlin.native.cocoapods"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    fun refreshVersionsPlugin(scope: PluginDependenciesSpec) : PluginDependencySpec =
            scope.id("de.fayard.refreshVersions").version(Versions.de_fayard_refreshversions_gradle_plugin)
    fun testLoggerPlugin(scope: PluginDependenciesSpec) : PluginDependencySpec =
        scope.id("com.adarshr.test-logger").version("2.0.0")
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

