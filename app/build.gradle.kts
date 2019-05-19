plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
plugins.apply(BuildPlugin::class)

android {
    defaultConfig {
        applicationId = "com.jonduran.circleci"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_APPCOMPAT)
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_ESPRESSO)
}
