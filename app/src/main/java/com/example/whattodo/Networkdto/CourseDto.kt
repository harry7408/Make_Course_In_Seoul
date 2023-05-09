package com.example.whattodo.Networkdto

import com.google.gson.annotations.SerializedName

/* 서버에서 코스만들때 요청하는 데이터 */
data class CourseDto(
    @SerializedName("numPeople")
    val numPeople: Int,
    @SerializedName("memberIdList")
    val memberIdList: List<Long>?,
    @SerializedName("startTime")
    val startTime: Int,
    @SerializedName("finishTime")
    val endTime: Int,
    @SerializedName("mealCheck")
    val mealFlag: Boolean,
    @SerializedName("wantedCategory")
    val essentialPlace: String?,
    @SerializedName("courseKeywords")
    val keywords: List<Int>,
    @SerializedName("goals")
    val specification: List<Int>,
)
