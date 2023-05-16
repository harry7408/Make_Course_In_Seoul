package com.example.whattodo.datas

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/* 사용자 정보 (회원가입 시 서버와 통신에 사용) */
data class User (
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
        val gender:String?,
        @SerializedName("fatigability")
        var fatigability:Int?,
        @SerializedName("specification")
        var specification:Int?,
        @SerializedName("activity")
        var active:Int?
        )





