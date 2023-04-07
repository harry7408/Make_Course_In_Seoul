package com.example.whattodo.network

import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    private const val BASE_URL="http://3.35.16.78:8080/"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val request : Request by lazy {
        retrofit.create(Request::class.java)
    }

}