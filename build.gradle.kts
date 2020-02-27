buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.6.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.1")
    }
}

plugins {
    id("de.fayard.dependencies")
}




allprojects {
    repositories {
        google()
        jcenter()
        
    }
}


tasks.register("clean").configure {
    delete("build")
}