package com.example.whattodo.network

import com.example.whattodo.datas.*
import com.example.whattodo.entity.Member
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/* 스프링 컨트롤러만봐라 바깥에 /member 박혀있으니 앞에 무조건 쓰기 */
interface idCheckAPI {
    @GET("/member/users")
    fun checkNickname() : Call<List<User>>
}

interface joinAPI {
    @POST("/member/join")
    fun Join(@Body data : User): Call<User>
}

interface findAPI {
    /* GET 방식 OR POST 방식? */
    @POST("/member/checkId")
    fun findId(@Body data:User): Call<User>

    @POST("/member/findPW")
    fun findPass(@Body data:User) : Call<User>
}

interface loginAPI {
    @POST("/login/login")
    fun login(@Body user:User) : Call<User>
}

interface ChangePassAPI {
    @POST("/member/changePW")
    fun change(@Body user:User, @Query("password") password:String) : Call<User>
}

interface withDrawAPI {
    @POST("/member/deleteMember")
    fun delete(@Body user:User):Call<String>
}

// 코스 제작할 때 보낼 데이터 api
/*interface courseAPI {
    @POST("/makeCourse/test")
    fun requestCourse() : Call<List<Course>>
}*/


/* 가게 테스트로 가게 데이터 받아오는 용도로 만든 api*/
interface storeAPI {
    @POST("/makeCourse/test")
    fun requestStore():Call<List<Store>>
}

interface addFriendAPI {
    @POST("")
    fun addFriend(@Body userId:String):Call<User>
}



