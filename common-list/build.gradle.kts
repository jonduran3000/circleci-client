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
    viewBinding { isEnabled = true }
}

dependencies {
    api(project(":common-android"))
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_RECYCLERVIEW)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
}