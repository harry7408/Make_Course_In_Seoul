package com.example.whattodo.Networkdto

import com.google.gson.annotations.SerializedName

data class StoreDto (
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