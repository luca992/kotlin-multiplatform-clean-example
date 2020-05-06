plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.adarshr.test-logger")
}

android {
    compileSdkVersion(rootProject.extra["AndroidSdk_compile"] as Int)
    defaultConfig {
        minSdkVersion(rootProject.extra["AndroidSdk_min"] as Int)
        targetSdkVersion(rootProject.extra["AndroidSdk_target"] as Int)
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
        val main by getting {}
        main.java.srcDirs("src/androidMain/kotlin")
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

kotlin {
    targets {
        android() {
            publishLibraryVariants("release", "debug")
        }
        //macosX64()
        iosX64()
        iosArm64()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:_")
                implementation(project(":domain"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
            }
        }
    }


}
