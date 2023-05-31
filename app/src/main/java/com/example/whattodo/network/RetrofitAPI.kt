package com.example.whattodo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitAPI {
//    10.0.2.2 로컬에서 테스트할 때
    private const val BASE_URL="http://10.0.2.2:8080"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val idCheckService : idCheckAPI = retrofit.create(idCheckAPI::class.java)
    val joinService : joinAPI=retrofit.create(joinAPI::class.java)
    val findService : findAPI= retrofit.create(findAPI::class.java)
    val loginService: loginAPI= retrofit.create(loginAPI::class.java)
    val changeService:ChangePassAPI= retrofit.create(ChangePassAPI::class.java)
    val deleteService: withDrawAPI=retrofit.create(withDrawAPI::class.java)
    /*val courseService : courseAPI=retrofit.create(courseAPI::class.java)*/
    val storeService:storeAPI=retrofit.create(storeAPI::class.java)
    val addFriendService:addFriendAPI= retrofit.create(addFriendAPI::class.java)

}

