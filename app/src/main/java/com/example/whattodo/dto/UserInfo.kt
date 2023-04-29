package com.example.whattodo.dto

import com.google.gson.annotations.SerializedName

// 회원가입에 관련된 사용자 정보
data class JoinData (
        @SerializedName("memberId")
        val memberId:String?,
        @SerializedName("password")
        val password:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("memberName")
        val memberName:String?,
        @SerializedName("birthday")
        val birthday:String?,
        @SerializedName("gender")
        val gender:String?
        )





