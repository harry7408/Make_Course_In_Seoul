package com.example.whattodo.network

import com.example.whattodo.dto.JoinData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface IdCheckAPI {
    @GET("/member/users")
    fun checkNickname() : Call<List<JoinData>>
}

interface JoinAPI {
    @GET("/join")
    fun Join(@Body data : JoinData): Call<JoinData>
}