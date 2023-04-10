package com.example.whattodo.network

import com.example.whattodo.dto.CheckIdData
import com.example.whattodo.dto.JoinData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceAPI {
     @POST("join/{users}")
     fun JoinUser(@Path("users") id :Int):Call<JoinData>

     @GET("join/{id}")
     fun checkNickname(@Path("id") flag:Boolean) : Call<CheckIdData>
}