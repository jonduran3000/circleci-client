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
    compileOptions {
        coreLibraryDesugaringEnabled = true
    }
}

dependencies {
    api(project(":cache"))
    api(project(":remote"))
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.ANDROIDX_ANNOTATION)
    api(Dependencies.ANDROIDX_SECURITY)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    coreLibraryDesugaring(Dependencies.JAVA_CORE_LIBS)
    implementation(Dependencies.STORE)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_ROOM_TESTING)
}