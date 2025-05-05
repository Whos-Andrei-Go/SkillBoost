// Top-level build.gradle.kts file

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
    }
}

plugins {
//    alias(libs.plugins.android.application) apply false
    id("com.android.application") version "8.5.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
