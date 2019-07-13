plugins {
    id(BuildPlugins.kotlinMultiplatform)
    id(BuildPlugins.kotlinCocoapods)
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "co.lucaspinazzola.example"
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
        main.res.srcDir("src/androidMain/res")
        val test by getting
        test.java.srcDirs("src/jvmTest/kotlin")
        val androidTest by getting
        test.java.srcDirs("src/androidTest/kotlin")
    }
}

//for CocoaPods
version = "1.0"

//https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

kotlin {
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Common kotlin code for SwiftUi hello world app"
        homepage = "https://github.com/luca992/kotlin-multiplatform-clean-template"
    }


    targets {
        jvm()
        android()
        macosX64()
        iosArm32()
        iosX64()
        iosArm64()
    }


    /*targets.filterIsInstance<KotlinNativeTarget>().forEach{
        it.binaries {
                sharedLib {}
        }
    }*/

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libraries.kotlinStdLibCommon)
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(project(":device"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libraries.kotlinStdLib)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libraries.appCompat)
                implementation(Libraries.ktxCore)
                implementation(Libraries.constraintLayout)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation (TestLibraries.junit4)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation (TestLibraries.testExtJUnit)
                implementation (TestLibraries.espresso)
            }
        }

    }

}
