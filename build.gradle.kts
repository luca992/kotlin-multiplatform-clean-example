buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath (Libs.com_android_tools_build_gradle)
        classpath (Libs.kotlin_gradle_plugin)
        classpath(Libs.kotlin_serialization)
        classpath(Libs.gradle_plugin)
        classpath(Libs.navigation_safe_args_gradle_plugin)
    }
}

plugins {
    BuildPlugins.refreshVersionsPlugin(this)
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://dl.bintray.com/icerockdev/moko") }
    }
}


tasks.register("clean").configure {
    delete("build")
}

task("setBuildForNative") {
    gradle.startParameter.taskNames.forEach { taskName ->
        when (taskName) {
            "buildSrcVersions", "refreshVersions", "dependencyUpdates" -> {
                buildForNative = true
                println("Set buildForNative: $buildForNative")
            }
            else -> {}
        }
    }
}
