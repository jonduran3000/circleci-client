object Versions {
    const val KOTLIN = "1.3.31"
    const val KOTLINX_COROUTINES = "1.2.1"
    const val DAGGER = "2.22.1"
    const val OKHTTP = "3.14.1"
    const val RETROFIT = "2.5.0"
}

object Plugins {
    const val ANDROID = "com.android.tools.build:gradle:3.5.0-beta01"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
}

object Dependencies {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val KOTLINX_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.0.2"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.0.2"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val JUNIT = "junit:junit:4.12"
    const val ANDROIDX_TEST_JUNIT = "androidx.test.ext:junit:1.1.0"
    const val ANDROIDX_TEST_ESPRESSO = "androidx.test.espresso:espresso-core:3.1.1"
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_COROUTINES_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    const val RETROFIT_SERIALIZATION_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0"
}