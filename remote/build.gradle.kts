plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}
plugins.apply(BuildPlugin::class)

android {
    compileOptions {
        coreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.KOTLINX_SERIALIZATION)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_ANDROID_COMPILER)
    api(Dependencies.OKHTTP)
    api(Dependencies.OKHTTP_LOGGING)
    api(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_SERIALIZATION_CONVERTER)
    coreLibraryDesugaring(Dependencies.JAVA_CORE_LIBS)
    testImplementation(Dependencies.JUNIT)
    testRuntimeOnly(Dependencies.JUNIT_ENGINE)
    testImplementation(Dependencies.JUNIT_PARAMS)
    testImplementation(Dependencies.TRUTH)
}
