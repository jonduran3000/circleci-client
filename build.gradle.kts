// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        jcenter()
    }
    dependencies {
        classpath(Plugins.ANDROID)
        classpath(Plugins.KOTLIN)
        classpath(Plugins.KOTLIN_SERIALIZATION)
        classpath(Plugins.HILT)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
