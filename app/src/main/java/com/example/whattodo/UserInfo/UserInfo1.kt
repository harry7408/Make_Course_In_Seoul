package com.example.whattodo.UserInfo

import com.google.gson.annotations.SerializedName

// 회원가입에 관련된 사용자 정보
data class UserInfo1 (
        @SerializedName("memberName")
        val email:String,
        @SerializedName("password")
        val password:String,
        @SerializedName("memberName")
        val name:String,
        @SerializedName("memberId")
        val nickname:String,
        @SerializedName("BDay")
        val birth:Int,
        val gender:String
        )

