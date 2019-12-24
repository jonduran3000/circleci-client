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
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_SERIALIZATION_CONVERTER)
}
