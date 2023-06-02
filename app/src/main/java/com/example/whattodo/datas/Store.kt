package com.example.whattodo.datas

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
/* 하나의 가게 정보를 받을 데이터 => 서버의 PlaceDto와 일치하도록 하기 */
data class Store (
    @SerializedName("id")
    val id:Int,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("cost")
    val cost:String?,
    @SerializedName("imgUrl")
    val imgUrl:String?,
    @SerializedName("phoneNumber")
    val phone: String?,
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("avgRating")
    val avgRating: Double?,
    @SerializedName("ratingNum")
    val ratingNum: Double?,
    @SerializedName("reviewNum")
    val reviewNum: Int?,
    @SerializedName("introduction")
    val introduction: String?,
    @SerializedName("tags")
    val tags: String?,
) : Parcelable






