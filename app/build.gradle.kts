plugins {
    id(BuildPlugins.kotlinMultiplatform)
    id(BuildPlugins.kotlinCocoapods)
    id("kotlin-kapt")
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroidExtensions)
    id("androidx.navigation.safeargs.kotlin")
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
    signingConfigs {
        create("release") {
            /*keyAlias = "release"
            keyPassword = "my release key password"
            storeFile = file("/home/miles/keystore.jks")
            storePassword = "my keystore password"*/
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets {
        val main by getting
        main.setRoot("src/androidMain")
        main.java.srcDir("src/androidMain/kotlin")
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
        main.res.srcDir("src/androidMain/res")
        main.assets.srcDir("src/androidMain/assets")
        val test by getting
        test.java.srcDir("src/androidTest/kotlin")
        val androidTest by getting
        androidTest.java.srcDir("src/androidAndroidTest/kotlin")
    }
    packagingOptions {
        pickFirst("META-INF/*")
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
                implementation(Libs.kotlin_stdlib_common)
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(project(":device"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_jdk8)
                implementation(Libs.appcompat)
                implementation(Libs.core_ktx)
                implementation(Libs.constraintlayout)
                implementation(Libs.navigation_fragment_ktx)
                implementation(Libs.navigation_ui_ktx)
                implementation(Libs.dagger)
                implementation(Libs.jsr250_api)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Libs.kotlin_test_common)
                implementation(Libs.kotlin_test_annotations_common)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Libs.kotlin_test)
                implementation(Libs.kotlin_test_junit)
            }
        }

        val androidAndroidTest by getting {
            dependencies {
                implementation(Libs.kotlin_test)
                implementation(Libs.junit_ktx)
                implementation(Libs.espresso_core)
            }
        }

    }

}

dependencies {
    "kapt"(Libs.dagger_compiler)
}
