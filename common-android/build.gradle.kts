plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}
plugins.apply(BuildPlugin::class)

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_ANNOTATION)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
}