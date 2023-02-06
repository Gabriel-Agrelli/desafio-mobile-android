package com.example.marveluniverse.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> buildRetrofit(`class`: Class<T>): T {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(InterceptorAPI())
        .build()

    return Retrofit
        .Builder()
        .baseUrl("https://gateway.marvel.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(`class`)
}