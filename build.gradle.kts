// AGP 9 uses built-in Kotlin. Pin the Kotlin Gradle plugin so the Compose
// compiler plugin and the compiler AGP invokes are the same version.
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.21")
    }
}

plugins {
    id("com.android.application") version "9.4.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
}
