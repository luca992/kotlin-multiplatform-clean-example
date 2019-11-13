buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath (Libs.com_android_tools_build_gradle)
        classpath (Libs.kotlin_gradle_plugin)
    }
}

plugins {
    BuildPlugins.refreshVersionsPlugin(this)
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