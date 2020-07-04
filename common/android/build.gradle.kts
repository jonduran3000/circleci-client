plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}
plugins.apply(BuildPlugin::class)

android {
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_ANNOTATION)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
}