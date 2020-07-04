plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
plugins.apply(BuildPlugin::class)

android {
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
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_ANDROID_COMPILER)
    coreLibraryDesugaring(Dependencies.JAVA_CORE_LIBS)
    implementation(Dependencies.STORE)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_ROOM_TESTING)
}