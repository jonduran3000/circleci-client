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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        coreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common:ui"))
    implementation(project(":common:utils"))
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_ACTIVITY)
    implementation(Dependencies.ANDROIDX_APPCOMPAT)
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Dependencies.ANDROIDX_FRAGMENT)
    implementation(Dependencies.ANDROIDX_LIFECYCLE)
    implementation(Dependencies.ANDROIDX_LIVEDATA)
    implementation(Dependencies.ANDROIDX_VIEWMODEL_SAVEDSTATE)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    implementation(Dependencies.DAGGER_ANDROID)
    kapt(Dependencies.DAGGER_ANDROID_COMPILER)
    compileOnly(Dependencies.ASSISTED_INJECT)
    kapt(Dependencies.ASSISTED_INJECT_COMPILER)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.KOTLINX_COROUTINES_ANDROID)
    implementation(Dependencies.GLIDE)
    kapt(Dependencies.GLIDE_COMPILER)
    implementation(Dependencies.MATERIAL)
    coreLibraryDesugaring(Dependencies.JAVA_CORE_LIBS)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_ESPRESSO)
}
