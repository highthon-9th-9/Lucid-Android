package com.example.lucid.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = ""

object Retrofit {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}