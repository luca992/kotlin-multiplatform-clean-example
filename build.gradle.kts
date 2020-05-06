buildscript {
    extra["buildForNative"] = "true"
    extra["AndroidSdk_min"] = 29
    extra["AndroidSdk_compile"] = 29
    extra["AndroidSdk_target"] = extra["AndroidSdk_compile"]
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://dl.bintray.com/icerockdev/moko") }
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean").configure {
    delete("build")
}

task("setBuildForNative") {
    gradle.startParameter.taskNames.forEach { taskName ->
        when (taskName) {
            "migrateToRefreshVersionsDependenciesConstants", "refreshVersions", "dependencyUpdates" -> {
                rootProject.extra["buildForNative"] = true
                println("Set buildForNative: ${rootProject.extra["buildForNative"]}")
            }
            else -> {}
        }
    }
}
