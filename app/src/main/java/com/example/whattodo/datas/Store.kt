package com.example.whattodo.datas

import com.google.gson.annotations.SerializedName


/* 하나의 가게 정보를 받을 데이터 */
data class Store (
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("categoryGroupCode")
    val categoryGroupCode: String,
    @SerializedName("categoryGroupName")
    val categoryGroupName: String,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("addressName")
    val addressName: String,
    @SerializedName("roadAddressName")
    val roadAddressName: String,
    @SerializedName("placeUrl")
    val placeUrl: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("startTime")
    val startTime: Double,
    @SerializedName("finishTime")
    val finishTime: Double,
        )



/* 여러개 가게 정보들 받을 데이터 */
data class StoreList (
    @SerializedName("courseList")
    val storeList:List<Store>,
)

