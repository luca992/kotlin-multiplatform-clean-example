
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
            "migrateToRefreshVersionsDependenciesConstants", "refreshVersions", "dependencyUpdates" -> {
                buildForNative = true
                println("Set buildForNative: $buildForNative")
            }
            else -> {}
        }
    }
}
