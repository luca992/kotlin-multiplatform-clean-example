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
                implementation(Libs.kotlin_stdlib_common)
                implementation(Libs.kotlinx_coroutines_core_common)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libs.kotlin_stdlib_jdk8)
                implementation(Libs.kotlinx_coroutines_core)
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.kotlinx_coroutines_core_native)
            }
        }

        val iosArm32Main by getting {}
        val iosX64Main by getting {}
        val iosArm64Main by getting {}
        val macosX64Main by getting {}


        configure(listOf(iosArm32Main,iosX64Main,iosArm64Main, macosX64Main)) {
            dependsOn(nativeMain)
            dependencies {
            }
        }

    }


}
