plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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
    implementation(Dependencies.ANDROIDX_VIEWMODEL)
    implementation(Dependencies.ANDROIDX_VIEWMODEL_SAVEDSTATE)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    implementation(Dependencies.ASSISTED_INJECT)
    kapt(Dependencies.ASSISTED_INJECT_COMPILER)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
}