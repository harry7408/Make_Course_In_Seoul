package com.example.whattodo.datas

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
/* 사용자 User로 맞추기 */
data class User (
        @SerializedName("userCode")
        val userCode:String?, // 사용자 식별용도 위한 값
        @SerializedName("id")
        val memberId:String?,
        @SerializedName("pw")
        val password:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("name")
        val memberName:String?,
        @SerializedName("birthDate")
        val birthday:String?,
        @SerializedName("gender")
        val gender:String?,
        @SerializedName("prefFatigue")
        var fatigability:Double?,
        @SerializedName("prefUnique")
        var specification:Double?,
        @SerializedName("prefActivity")
        var active:Double?
        ) : Parcelable





