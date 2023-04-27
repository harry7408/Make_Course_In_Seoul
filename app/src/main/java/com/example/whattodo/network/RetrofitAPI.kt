package com.example.whattodo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
//    10.0.2.2 앱에서
    private const val BASE_URL="http://54.180.123.171:8080"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val idCheckService : IdCheckAPI = retrofit.create(IdCheckAPI::class.java)
    val joinService : JoinAPI=retrofit.create(JoinAPI::class.java)

}

