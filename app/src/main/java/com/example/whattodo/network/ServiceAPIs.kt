package com.example.whattodo.network

import android.graphics.Paint.Join
import com.example.whattodo.dto.JoinData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/* 스프링 컨트롤러만봐라 바깥에 /member 박혀있으니 앞에 무조건 쓰기 */
interface idCheckAPI {
    @GET("/member/users")
    fun checkNickname() : Call<List<JoinData>>
}

interface joinAPI {
    @POST("/member/join")
    fun Join(@Body data : JoinData): Call<JoinData>
}

interface findAPI {
    /* GET 방식 OR POST 방식? */
    @POST("/membercheckId")
    fun findId(@Body data:JoinData): Call<JoinData>

    @POST("/memberfindPW")
    fun findPass(@Body data:JoinData) : Call<JoinData>
}

//@Body data:JoinData