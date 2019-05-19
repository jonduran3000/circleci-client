plugins {
    kotlin("jvm")
    kotlin("kapt")
}
plugins.apply(BuildPlugin::class)

dependencies {
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_COROUTINES_ADAPTER)
    implementation(Dependencies.RETROFIT_SERIALIZATION_CONVERTER)
}
