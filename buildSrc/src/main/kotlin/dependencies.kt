import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

var buildForNative = true

object BuildPlugins {
    fun testLoggerPlugin(scope: PluginDependenciesSpec) : PluginDependencySpec =
        scope.id("com.adarshr.test-logger").version("2.0.0")
}


object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

