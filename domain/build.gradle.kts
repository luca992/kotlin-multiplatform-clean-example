plugins {
    kotlin("multiplatform")
    BuildPlugins.testLoggerPlugin(this)
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
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:_")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.mockk:mockk-common:_")
                implementation("org.jetbrains.kotlin:kotlin-test-common:_")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.mockk:mockk:_")
                implementation("org.jetbrains.kotlin:kotlin-test:_")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:_")
                runtimeOnly("net.bytebuddy:byte-buddy:_") //https://github.com/mockk/mockk/issues/376
            }
        }
        if (buildForNative) {
            val nativeCommonMain by creating {
                dependsOn(commonMain)
                dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:_")
                }
            }
            val nativeCommonTest by creating {}
            val iosX64Main by getting {}
            val iosArm64Main by getting {}
            //val macosX64Main by getting {}
            val iosX64Test by getting {}
            val iosArm64Test by getting {}


            configure(listOf(iosX64Main, iosArm64Main/*, macosX64Main*/)) {
                dependsOn(nativeCommonMain)
                dependencies {
                }
            }
            configure(listOf(iosX64Test, iosArm64Test/*, macosX64Test*/)) {
                dependsOn(nativeCommonTest)
            }
        }
    }


}
