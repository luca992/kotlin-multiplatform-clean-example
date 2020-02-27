plugins {
    id(BuildPlugins.kotlinMultiplatform)
    id(BuildPlugins.kotlinCocoapods)
    id("kotlin-kapt")
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroidExtensions)
    id("androidx.navigation.safeargs.kotlin")
    id("de.fayard.dependencies")
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
            keyAlias = "release"
            keyPassword = "my release key password"
            storeFile = file("/home/miles/keystore.jks")
            storePassword = "my keystore password"
        }
    }
    buildTypes {
        getByName("release") {
            //signingConfig = signingConfigs.getByName("release")
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
        pickFirst("META-INF/*.kotlin_module")
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

//for CocoaPods
version = "1.0"


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
                implementation(Kotlin.stdlib.common)
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(project(":device"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Kotlin.stdlib.jdk8)
                implementation(AndroidX.appCompat)
                implementation(AndroidX.coreKtx)
                implementation(AndroidX.constraintLayout)
                implementation(AndroidX.navigation.fragmentKtx)
                implementation(AndroidX.navigation.uiKtx)
                implementation(Ktor.client.cio)
                implementation(Libs.dagger)
                implementation(Libs.jsr250_api)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Kotlin.test.common)
                implementation(Kotlin.test.annotationsCommon)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Testing.kotlinTest)
                implementation(Libs.kotlin_test_junit)
            }
        }

        val androidAndroidTest by getting {
            dependencies {
                implementation(Libs.kotlin_test)
                implementation(Libs.junit_ktx)
                implementation(de.fayard.dependencies.DependenciesPlugin.artifactVersionKeyRules AndroidX.test.espresso.core)
            }
        }

    }

}

dependencies {
    "kapt"( Libs.dagger_compiler)
}
