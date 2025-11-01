package com.alaa.alaagallo.util

import com.alaa.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccountsInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${Constants.TOKEN}")
            .header("Accept","application/json")
            .build()

        val response = chain.proceed(request)

        return response
    }

}
