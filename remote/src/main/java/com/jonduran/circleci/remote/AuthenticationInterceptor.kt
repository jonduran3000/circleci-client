package com.jonduran.circleci.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = Credentials.basic(
            "5018bb4505a0dfca1a15985aec8ac06af6bc1099",
            ""
        )

        val request = chain.request().newBuilder()
            .addHeader("Authorization", credentials)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}