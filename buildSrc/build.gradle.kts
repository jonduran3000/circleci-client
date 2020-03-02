plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.0-beta01")
}