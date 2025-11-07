package com.br.cinefiles.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApiClient {

    // 1. Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // See full request and response
    }

    // 2. Create a custom OkHttpClient and add the interceptor
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 3. Build Retrofit with the custom client
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient) // Use the client with the logger
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java)
    }
}
