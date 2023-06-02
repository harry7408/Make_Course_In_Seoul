package com.example.whattodo.datas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* 장소 지도보기 누를 때 사용 */
@Parcelize
data class Coordinate (
    val x:Double,
    val y:Double,
        ) : Parcelable