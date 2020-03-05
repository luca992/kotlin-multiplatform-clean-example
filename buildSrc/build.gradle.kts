import java.util.Properties

plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

val versionsProperties : Properties
    get() {
        val versions = Properties()
        val localProperties: File = rootProject.file("versions.properties")
        if (localProperties.exists()) {
            localProperties.inputStream().use { versions.load(it) }
        }
        return versions
    }