plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("kotlinx-serialization")
}
plugins.apply(BuildPlugin::class)

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.KOTLINX_SERIALIZATION)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    api(Dependencies.OKHTTP)
    api(Dependencies.OKHTTP_LOGGING)
    api(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_SERIALIZATION_CONVERTER)
    implementation(Dependencies.THREETEN_BP)
    testImplementation(Dependencies.JUNIT)
    testRuntimeOnly(Dependencies.JUNIT_ENGINE)
    testImplementation(Dependencies.JUNIT_PARAMS)
    testImplementation(Dependencies.TRUTH)
}
