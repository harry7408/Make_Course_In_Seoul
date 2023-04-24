package com.example.whattodo.dto

import com.google.gson.annotations.SerializedName

// 회원가입에 관련된 사용자 정보
data class JoinData (
        @SerializedName("memberId")
        val memberId:String,
        @SerializedName("memberName")
        val memberName:String,
        @SerializedName("password")
        val password:String,
        @SerializedName("eMail")
        val eMail:String,
        @SerializedName("BDay")
        val BDay:String,
        @SerializedName("gender")
        val gender:String
        )



data class CheckIdMessage(
        val data:List<JoinData>?
)

