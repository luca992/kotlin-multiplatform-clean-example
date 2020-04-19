plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
    id("com.android.library")
    BuildPlugins.testLoggerPlugin(this)
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
    lintOptions {
        disable("GradleDependency")
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
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:_")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:_")
                implementation("io.ktor:ktor-client-core:_")
                implementation("io.ktor:ktor-client-logging:_")
                implementation("io.ktor:ktor-client-json:_")
                implementation("io.ktor:ktor-client-serialization:_")
                implementation("io.ktor:ktor-client-auth:_")
                implementation("io.ktor:ktor-utils:_")
                implementation("com.squareup.sqldelight:runtime:_")
                implementation("com.squareup.sqldelight:coroutines-extensions:_")
                implementation("co.touchlab:stately-common:_")
                implementation("com.soywiz.korlibs.klock:klock:_")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.mockk:mockk-common:_")
                implementation("org.jetbrains.kotlin:kotlin-test-common:_")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
                implementation("io.ktor:ktor-client-mock:_")
                implementation("io.ktor:ktor-client-json:_")
                implementation("io.ktor:ktor-client-serialization:_")
                implementation("co.touchlab:stately-concurrency:_")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:_")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:_")
                implementation("io.ktor:ktor-client-android:_")
                implementation("io.ktor:ktor-client-logging-jvm:_")
                implementation("io.ktor:ktor-client-json-jvm:_")
                implementation("io.ktor:ktor-client-serialization-jvm:_")
                implementation("io.ktor:ktor-client-auth-jvm:_")
                implementation("io.ktor:ktor-utils-jvm:_")
                implementation("com.squareup.sqldelight:android-driver:_")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation("io.mockk:mockk:_")
                implementation("org.jetbrains.kotlin:kotlin-test:_")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:_")
                implementation("com.squareup.sqldelight:sqlite-driver:_")
                implementation("io.ktor:ktor-client-mock-jvm:_")
                implementation("io.ktor:ktor-client-json-jvm:_")
                implementation("io.ktor:ktor-client-serialization-jvm:_")
            }
        }

        if (buildForNative) {
            val nativeCommonMain by creating {
                dependsOn(commonMain)
                dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:_")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:_")
                    implementation("io.ktor:ktor-client-core-native:_")
                    implementation("io.ktor:ktor-client-logging-native:_")
                    implementation("io.ktor:ktor-client-json-native:_")
                    implementation("io.ktor:ktor-client-serialization-native:_")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:_")
                    implementation("io.ktor:ktor-client-auth-native:_")
                    implementation("io.ktor:ktor-utils-native:_")
                    implementation("com.squareup.sqldelight:native-driver:_")
                }
            }
            val nativeCommonTest by creating {}
            val iosX64Main by getting {}
            val iosArm64Main by getting {}
            val iosX64Test by getting {}
            val iosArm64Test by getting {}
            /*val macosX64Main by getting {
                dependsOn(nativeMain)
                dependencies {
                    implementation("io.ktor:ktor-client-curl:_")
                }
            }*/


            configure(listOf(iosX64Main, iosArm64Main)) {
                dependsOn(nativeCommonMain)
                dependencies {
                    implementation("io.ktor:ktor-client-ios:_")
                }
            }
            configure(listOf(iosX64Test, iosArm64Test)) {
                dependsOn(nativeCommonTest)
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