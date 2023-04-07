package com.example.whattodo.network

import retrofit2.Retrofit
import retrofit2.http.POST

object RetrofitClient {
    lateinit var retrofit : Retrofit
    val BASE_URL:String=""
}