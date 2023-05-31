package com.example.whattodo.datas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Coordinate (
    val x:Double,
    val y:Double,
        ) : Parcelable