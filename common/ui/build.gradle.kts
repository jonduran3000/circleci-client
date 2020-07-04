plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}
plugins.apply(BuildPlugin::class)

android {
    buildTypes {
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(project(":common:android"))
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_ACTIVITY)
    implementation(Dependencies.ANDROIDX_APPCOMPAT)
    implementation(Dependencies.ANDROIDX_FRAGMENT)
    implementation(Dependencies.ANDROIDX_LIFECYCLE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_EXT)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_JAVA8)
    implementation(Dependencies.ANDROIDX_RECYCLERVIEW)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
}