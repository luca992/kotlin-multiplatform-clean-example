plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
    id("com.android.library")
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
        android {
            publishLibraryVariants("release", "debug")
        }
        if (buildForNative) {
            //macosX64()
            iosX64()
            iosArm64()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain"))
                implementation(Libs.kotlin_stdlib_common)
                implementation(Libs.kotlinx_coroutines_core_common)
                implementation(Libs.kotlinx_serialization_runtime_common)
                implementation(Libs.ktor_client_core)
                implementation(Libs.ktor_client_logging)
                implementation(Libs.ktor_client_json)
                implementation(Libs.ktor_client_serialization)
                implementation(Libs.ktor_client_auth)
                implementation(Libs.ktor_utils)
                implementation(Libs.com_squareup_sqldelight_runtime)
                implementation(Libs.stately)
                implementation(Libs.stately_collections)
            }
        }

        val commonTest by getting {
            dependencies {
                //implementation(project(":domain"))
                implementation(Libs.mockk_common)
                implementation(Libs.kotlin_test_common)
                implementation(Libs.kotlin_test_annotations_common)
                implementation(Libs.kotlinx_coroutines_test)
                implementation(Libs.ktor_client_mock)
                implementation(Libs.ktor_client_json)
                implementation(Libs.ktor_client_serialization)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_jdk8)
                implementation(Libs.kotlinx_coroutines_android)
                implementation(Libs.kotlinx_serialization_runtime)
                implementation(Libs.ktor_client_android)
                implementation(Libs.ktor_client_logging_jvm)
                implementation(Libs.ktor_client_json_jvm)
                implementation(Libs.ktor_client_serialization_jvm)
                implementation(Libs.ktor_client_auth_jvm)
                implementation(Libs.ktor_utils_jvm)
                implementation(Libs.android_driver)
                implementation(Libs.stately_jvm)
                implementation(Libs.stately_collections_jvm)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Libs.mockk)
                implementation(Libs.kotlin_test)
                implementation(Libs.kotlin_test_junit)
                implementation(Libs.sqlite_driver)
                implementation(Libs.ktor_client_mock_jvm)
                implementation(Libs.ktor_client_json_jvm)
                implementation(Libs.ktor_client_serialization_jvm)
            }
        }

        if (buildForNative) {
            val nativeMain by creating {
                dependsOn(commonMain)
                dependencies {
                    implementation(Libs.kotlinx_coroutines_core_native)
                    implementation(Libs.kotlinx_serialization_runtime_native)
                    implementation(Libs.ktor_client_core_native)
                    implementation(Libs.ktor_client_logging_native)
                    implementation(Libs.ktor_client_logging_native)
                    implementation(Libs.kotlinx_serialization_runtime_native)
                    //implementation(Libs.ktor_client_auth_native)
                    implementation(Libs.ktor_utils_native)
                    implementation(Libs.native_driver)
                }
            }

            val iosX64Main by getting {}
            val iosArm64Main by getting {}
            /*val macosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
                implementation(Libs.ktor_client_curl)
            }
            }*/


            configure(listOf(iosX64Main, iosArm64Main)) {
                dependsOn(nativeMain)
                dependencies {
                    implementation(Libs.ktor_client_ios)
                }
            }
        }

    }


}

sqldelight{
    database("Database") {
        packageName = "co.lucaspinazzola.example.data.model.sqldelight"
        schemaOutputDirectory = file("src/commonMain/sqldelight/migrations")
    }
}