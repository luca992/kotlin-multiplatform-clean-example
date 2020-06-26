import java.util.Properties
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency


plugins {
    id("com.android.application")
    //id("kotlin-multiplatform")
    id("kotlin-android")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.adarshr.test-logger")
}

fun getProperties(path: String) : Properties {
    val properties = Properties()
    val file: File = rootProject.file(path)
    if (file.exists()) {
        file.inputStream().use { properties.load(it) }
    }
    return properties
}

val giphyApiKey : String
    get() {
        val local = getProperties("local.properties")
        return local.getProperty("giphyApiKey") ?: "Add giphyApiKey to local.properties"
    }

val versionsProperties : Properties by lazy {
    val versions = getProperties("versions.properties")
    versions
}


android {
    compileSdkVersion(rootProject.extra["AndroidSdk_compile"] as Int)
    defaultConfig {
        applicationId = "co.lucaspinazzola.example"
        minSdkVersion(rootProject.extra["AndroidSdk_min"] as Int)
        targetSdkVersion(rootProject.extra["AndroidSdk_target"] as Int)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "GIPHY_API_KEY", "\"$giphyApiKey\"")
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
        val main by getting {}
        main.setRoot("src/androidMain")
        main.java.srcDirs("src/androidMain/kotlin", "src/commonMain/kotlin")
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
        main.res.srcDir("src/androidMain/res")
        main.assets.srcDir("src/androidMain/assets")
        val test by getting {}
        test.java.srcDir("src/androidTest/kotlin")
        val androidTest by getting {}
        androidTest.java.srcDir("src/androidAndroidTest/kotlin")
    }
    lintOptions {
        disable("GradleDependency")
    }
    packagingOptions {
        pickFirst("META-INF/*.kotlin_module")
        pickFirst("META-INF/*.kotlin_metadata")
    }

    buildFeatures {
        // Enables Jetpack Compose for this module
        dataBinding = true
        viewBinding = true
        compose = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        composeOptions.kotlinCompilerVersion = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
        kotlinCompilerExtensionVersion = versionsProperties["version.androidx.ui"].toString()
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
    kotlinOptions {
        jvmTarget = "1.8"
        //useIR= true
    }
}
/*
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin" && requested.module.name == "kotlin-compiler-embeddable") {
            useVersion("1.3.70-dev-withExperimentalGoogleExtensions-20200424")
        }
        if (requested.group == "androidx.compose" && requested.module.name == "compose-compiler") {
            useVersion("0.1.0-dev13")
        }
    }
}*/
/*
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
            //macosX64()
            iosX64()
            iosArm64()
        }


        targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
            .forEach {
                it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
                    .forEach { lib ->
                        lib.export(project(":domain"))
                        lib.export(project(":data"))
                        lib.export(project(":device"))
                        //lib.transitiveExport = true
                    }
            }

        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib-common:_")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:_")
                    implementation("dev.icerock.moko:mvvm:_")

                    api(project(":domain"))
                    api(project(":data"))
                    api(project(":device"))
                }
            }

            val androidMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:_")

                    implementation("androidx.appcompat:appcompat:_")
                    implementation("androidx.core:core-ktx:_")
                    implementation("androidx.constraintlayout:constraintlayout:_")
                    implementation("androidx.navigation:navigation-fragment-ktx:_")
                    implementation("androidx.navigation:navigation-ui-ktx:_")
                    implementation("com.github.luca992:compose-router:81428811b5")

                    implementation("androidx.ui:ui-core:_")
                    implementation("androidx.ui:ui-tooling:_")
                    implementation("androidx.ui:ui-layout:_")
                    implementation("androidx.ui:ui-material:_")
                    implementation("androidx.ui:ui-livedata:_")
                    implementation("com.github.luca992:coil-composable:_")


                    implementation("com.google.dagger:dagger:_")
                    lintChecks("com.google.dagger:dagger-lint:_")
                    implementation("androidx.lifecycle:lifecycle-livedata:_")
                    implementation("androidx.lifecycle:lifecycle-common-java8:_")
                    implementation("javax.annotation:jsr250-api:_")
                    implementation("io.coil-kt:coil:_")
                    implementation("io.coil-kt:coil-gif:_")

                    configurations["kapt"].dependencies
                        .add(
                            org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                                "com.google.dagger",
                                "dagger-compiler",
                                "_"
                            )
                        )
                }
            }

            val commonTest by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-test-common:_")
                    implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
                    implementation("io.mockk:mockk-common:_")
                }
            }

            val androidTest by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-test:_")
                    implementation("org.jetbrains.kotlin:kotlin-test-junit:_")
                    implementation("io.mockk:mockk:_")
                    implementation("androidx.arch.core:core-testing:_")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
                }
            }

            val androidAndroidTest by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-test:_")
                    implementation("androidx.test.ext:junit-ktx:_")
                    implementation("androidx.test.espresso:espresso-core:_")
                    implementation("io.mockk:mockk-android:_")
                }
            }

        }

    }
*/




dependencies {
    implementation("dev.icerock.moko:mvvm:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":device"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:_")

    implementation("androidx.appcompat:appcompat:_")
    implementation("androidx.core:core-ktx:_")
    implementation("androidx.constraintlayout:constraintlayout:_")
    implementation("androidx.navigation:navigation-fragment-ktx:_")
    implementation("androidx.navigation:navigation-ui-ktx:_")
    implementation("com.github.luca992:compose-router:ae7e61d0c3")

    implementation("androidx.ui:ui-core:_")
    implementation("androidx.ui:ui-tooling:_")
    implementation("androidx.ui:ui-layout:_")
    implementation("androidx.ui:ui-material:_")
    implementation("androidx.ui:ui-livedata:_")
    implementation("com.github.luca992:coil-composable:_")


    implementation("com.google.dagger:dagger:_")
    lintChecks("com.google.dagger:dagger-lint:${versionsProperties["version.com.google.dagger..dagger"].toString()}")
    kapt ("com.google.dagger:dagger-compiler:_")
    implementation("androidx.lifecycle:lifecycle-livedata:_")
    implementation("androidx.lifecycle:lifecycle-common-java8:_")
    implementation("javax.annotation:jsr250-api:_")
    implementation("io.coil-kt:coil:_")
    implementation("io.coil-kt:coil-gif:_")

}
