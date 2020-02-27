plugins {
    kotlin("multiplatform")
}

kotlin {
    targets {
        jvm()
        if (buildForNative) {
            //macosX64()
            iosX64()
            iosArm64()
        }
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

        val commonTest by getting {
            dependencies {
                implementation(Libs.mockk_common)
                implementation(Libs.kotlin_test_common)
                implementation(Libs.kotlin_test_annotations_common)
                implementation(Libs.kotlinx_coroutines_test)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(Libs.mockk)
                implementation(Libs.kotlin_test)
                implementation(Libs.kotlin_test_junit)
                runtimeOnly(Libs.byte_buddy) //https://github.com/mockk/mockk/issues/376
            }
        }
        if (buildForNative) {
            val nativeMain by creating {
                dependsOn(commonMain)
                dependencies {
                    implementation(Libs.kotlinx_coroutines_core_native)
                }
            }

            val iosX64Main by getting {}
            val iosArm64Main by getting {}
            //val macosX64Main by getting {}


            configure(listOf(iosX64Main, iosArm64Main/*, macosX64Main*/)) {
                dependsOn(nativeMain)
                dependencies {
                }
            }
        }
    }


}
