plugins {
    kotlin("multiplatform")
    id(BuildPlugins.androidLibrary)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets {
        val main by getting
        main.java.srcDirs("src/androidMain/kotlin")
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

kotlin {
    targets {
        android() {
            publishLibraryVariants("release", "debug")
        }
        macosX64()
        iosArm32()
        iosX64()
        iosArm64()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_common)
                implementation(project(":domain"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_jdk8)
            }
        }
    }


}
