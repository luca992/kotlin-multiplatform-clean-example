plugins {
    kotlin("multiplatform")
}

kotlin {
    targets {
        jvm()
        macosX64()
        iosArm32()
        iosX64()
        iosArm64()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain"))
                implementation(Libs.kotlin_stdlib_common)
                implementation(Libs.kotlinx_coroutines_core_common)
                implementation(Libs.ktor_client_core)
                implementation(Libs.ktor_client_logging)
                implementation(Libs.ktor_client_serialization)
                implementation(Libs.ktor_client_auth)
                implementation(Libs.ktor_utils)

            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_jdk8)
                implementation(Libs.kotlinx_coroutines_core)
                implementation(Libs.kotlinx_serialization_runtime)
                implementation(Libs.ktor_client_core_jvm)
                implementation(Libs.ktor_client_okhttp)
                implementation(Libs.ktor_client_logging_jvm)
                implementation(Libs.ktor_client_serialization_jvm)
                implementation(Libs.ktor_client_auth_jvm)
                implementation(Libs.ktor_utils_jvm)

            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.kotlinx_coroutines_core_native)
                implementation(Libs.kotlinx_serialization_runtime_native)
                implementation(Libs.ktor_client_core_native)
                implementation(Libs.ktor_client_okhttp)
                implementation(Libs.ktor_client_logging_native)
                implementation(Libs.kotlinx_serialization_runtime_native)
                implementation(Libs.ktor_client_auth_native)
                implementation(Libs.ktor_utils_native)
            }
        }

        val iosArm32Main by getting {}
        val iosX64Main by getting {}
        val iosArm64Main by getting {}
        val macosX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
                implementation(Libs.ktor_client_curl)
            }
        }


        configure(listOf(iosArm32Main,iosX64Main,iosArm64Main)) {
            dependsOn(nativeMain)
            dependencies {
                implementation(Libs.ktor_client_ios)
            }
        }

    }


}
