package com.jonduran.circleci.data

import com.jonduran.circleci.remote.Authorization
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Authorization
class AuthorizationInterceptor @Inject constructor(
    private val dataStore: Lazy<DataStore>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = runBlocking { dataStore.get().getCredentials() }

        val builder = chain.request().newBuilder()
        if (credentials != null) {
            builder.addHeader("Authorization", credentials)
        }

        return chain.proceed(builder.build())
    }
}