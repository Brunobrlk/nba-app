package com.example.nbaapp.data.remote

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    // Must be dynamically injected through a proper api
    private val token = "27d8ad6d-56c7-4a29-a61b-dac75613be11"
    override fun intercept(chain: Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}
