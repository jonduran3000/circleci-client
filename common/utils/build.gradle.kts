plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("kotlinx-serialization")
}
plugins.apply(BuildPlugin::class)

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
}
