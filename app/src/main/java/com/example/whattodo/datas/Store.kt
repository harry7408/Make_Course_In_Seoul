package com.example.whattodo.datas

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
/* 하나의 가게 정보를 받을 데이터 => 서버의 PlaceDto와 일치하도록 하기 */
data class Store (
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val placeName: String,
    @SerializedName("categoryCode")
    val categoryCode: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("cost")
    val cost:String,
    @SerializedName("imgUrl")
    val imgUrl:String,
    @SerializedName("phoneNumber")
    val phone: String,

//    @SerializedName("categoryGroupName")
//    val categoryGroupName: String,
//    @SerializedName("categoryName")
//    val categoryName: String,
//    @SerializedName("roadAddressName")
//    val roadAddressName: String,
//    @SerializedName("placeUrl")
//    val placeUrl: String,
//    @SerializedName("rate")
//    val rate: Double,
//    @SerializedName("startTime")
//    val startTime: Double,
//    @SerializedName("finishTime")
//    val finishTime: Double,
        ) : Parcelable

@Parcelize
data class StoreList(
    val storeList:List<Store>
) : Parcelable




