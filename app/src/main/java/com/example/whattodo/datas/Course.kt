package com.example.whattodo.datas

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Courses(
    val courses: List<Course>
)


/* 서버한테 전달할 데이터 */
@Parcelize
data class Course(
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
    @SerializedName("categoryC")
    val categoryC:String,
    @SerializedName("goals")
    val specification: List<Int>,
) : Parcelable
