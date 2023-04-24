package com.example.whattodo.network

import android.app.Service
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitAPI {
    private const val BASE_URL="http://13.125.219.98:8080"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : ServiceAPI = retrofit.create(ServiceAPI::class.java)
}

