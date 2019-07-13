plugins {
    kotlin("multiplatform")
}

//https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

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
                implementation(Libraries.kotlinStdLibCommon)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libraries.kotlinStdLib)
            }
        }

    }


}
