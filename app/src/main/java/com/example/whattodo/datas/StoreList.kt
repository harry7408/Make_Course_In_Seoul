package com.example.whattodo.datas

import com.google.gson.annotations.SerializedName


/* 가게 정보들 받을 데이터 */
data class StoreList (
    @SerializedName("courseList")
    val courseList:List<Store>,
        )