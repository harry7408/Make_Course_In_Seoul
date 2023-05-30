package com.example.whattodo.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="friends")
data class Friend(
    @PrimaryKey
    val memberId:String,
    val memberName:String,
    val gender:String,
):Parcelable