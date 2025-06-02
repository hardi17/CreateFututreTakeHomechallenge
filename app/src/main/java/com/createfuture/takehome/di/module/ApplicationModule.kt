package com.createfuture.takehome.di.module

import com.createfuture.takehome.data.api.ApiKeyInterceptor
import com.createfuture.takehome.data.api.Service
import com.createfuture.takehome.di.AuthorizationKey
import com.createfuture.takehome.di.BaseUrl
import com.createfuture.takehome.utils.AppConst.AUTH_KEY
import com.createfuture.takehome.utils.AppConst.BASE_URL
import com.createfuture.takehome.utils.DefaultDispatcherProvider
import com.createfuture.takehome.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Service {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(Service::class.java)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@AuthorizationKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)


    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        // Log request + response bodies
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl() = BASE_URL

    @AuthorizationKey
    @Provides
    fun provideApiKey(): String = AUTH_KEY

    @Provides
    @Singleton
    fun provideDispatcher() : DispatcherProvider = DefaultDispatcherProvider()

}