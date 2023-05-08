package com.example.whattodo.network

import com.example.whattodo.Networkdto.CourseDto
import com.example.whattodo.Networkdto.StoreDto
import com.example.whattodo.Networkdto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/* 스프링 컨트롤러만봐라 바깥에 /member 박혀있으니 앞에 무조건 쓰기 */
interface idCheckAPI {
    @GET("/member/users")
    fun checkNickname() : Call<List<UserDto>>
}

interface joinAPI {
    @POST("/member/join")
    fun Join(@Body data : UserDto): Call<UserDto>
}

interface findAPI {
    /* GET 방식 OR POST 방식? */
    @POST("/member/checkId")
    fun findId(@Body data:UserDto): Call<UserDto>

    @POST("/member/findPW")
    fun findPass(@Body data:UserDto) : Call<UserDto>
}

interface loginAPI {
    @POST("/login/login")
    fun login(@Body data:UserDto) : Call<UserDto>
}
/* 코스 만들때 쓰는 API */
interface courseAPI {
    @POST("/makeCourse")
    fun requestCourse(@Body courseDto: CourseDto) : Call<CourseDto>
}

interface storeAPI {
    @GET("a")
    fun requestStore():Call<StoreDto>
}

