package com.example.whattodo.datas

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
/* 하나의 가게 정보를 받을 데이터 => 서버의 PlaceDto와 일치하도록 하기 */
data class Store (
    @SerializedName("id")
    val id:Int,
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("rating")
    val avgRating: Double?,
    @SerializedName("ratingNum")
    val ratingNum: Int?,
    @SerializedName("reviewNum")
    val reviewNum: Int?,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("phoneNumber")
    val phone: String?,
    @SerializedName("intro")
    val introduction: String?,
    @SerializedName("time")
    val time:String?,
    @SerializedName("menu")
    val menu:String?,
    @SerializedName("imgUrl")
    val imgUrl:String?,
    @SerializedName("tag")
    val tags: String?,
) : Parcelable






