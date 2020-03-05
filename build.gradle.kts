buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.6.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.61")
        classpath("com.squareup.sqldelight:gradle-plugin:1.2.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.1")
    }
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
            "migrateToRefreshVersionsDependenciesConstants", "refreshVersions", "dependencyUpdates" -> {
                buildForNative = true
                println("Set buildForNative: $buildForNative")
            }
            else -> {}
        }
    }
}
