import java.util.Properties
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("kotlin-kapt")
    id("com.android.application")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    BuildPlugins.testLoggerPlugin(this)
}


val giphyApiKey : String
    get() {
    val local = Properties()
    val localProperties: File = rootProject.file("local.properties")
    if (localProperties.exists()) {
        localProperties.inputStream().use { local.load(it) }
    }
    return local.getProperty("giphyApiKey") ?: "Add giphyApiKey to local.properties"
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
    lintOptions {
        disable("GradleDependency")
    }
    packagingOptions {
        pickFirst("META-INF/*.kotlin_module")
        pickFirst("META-INF/*.kotlin_metadata")
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        isEnabled = true
    }
    viewBinding {
        isEnabled = true
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
    kotlinOptions {
        jvmTarget = "1.8"
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
        if (buildForNative) {
            //macosX64()
            iosX64()
            iosArm64()
        }
    }


    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().forEach{
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
                implementation("com.google.dagger:dagger:_")
                implementation("androidx.lifecycle:lifecycle-livedata:_")
                implementation("androidx.lifecycle:lifecycle-common-java8:_")
                implementation("javax.annotation:jsr250-api:_")
                implementation("io.coil-kt:coil:_")
                implementation("io.coil-kt:coil-gif:_")

                configurations["kapt"].dependencies
                        .add(DefaultExternalModuleDependency("com.google.dagger", "dagger-compiler", "_"))
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
