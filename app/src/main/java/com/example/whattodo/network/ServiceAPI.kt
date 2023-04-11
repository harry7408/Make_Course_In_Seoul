package com.example.whattodo.network


import com.example.whattodo.dto.CheckIdMessage
import com.example.whattodo.dto.JoinData
import retrofit2.Call
import retrofit2.http.*

interface ServiceAPI {
     @POST("join.html")
     fun JoinUser(@Field("users") id :Int):Call<JoinData>

     @GET("/member/users")
     fun checkNickname() : Call<CheckIdMessage>
}