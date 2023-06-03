package com.example.whattodo.datas

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    @SerializedName("userCode")
    val userCode:String,
    @SerializedName("name")
    val userName:String,
    @SerializedName("gender")
    val userGender:String,
) : Parcelable
