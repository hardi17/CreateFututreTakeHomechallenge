package com.createfuture.takehome.data.api

import com.createfuture.takehome.di.AuthorizationKey
import okhttp3.Interceptor
import okhttp3.Response

/*
* Interceptor for pass authorization key to the
* request header to enable authenticated  access to api call
* */
class ApiKeyInterceptor(@AuthorizationKey private val authorizationKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("Authorization", authorizationKey)
        return chain.proceed(requestBuilder.build())
    }

}