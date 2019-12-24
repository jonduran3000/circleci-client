package com.jonduran.circleci.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
object RemoteModule {
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(client: Lazy<OkHttpClient>): CircleCiApi {
        val json = Json.nonstrict
        val contentType = "application/json".toMediaType()
        val factory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl("https://circleci.com/api/v1.1/")
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call {
                    return client.get().newCall(request)
                }
            })
            .addConverterFactory(factory)
            .build()
            .create()
    }
}
