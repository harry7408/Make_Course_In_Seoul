package com.example.whattodo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "members")
data class Member(
    @PrimaryKey
    var memberId:String,

    var password:String?,

    var email:String?,

    var memberName:String?,

    var birthday:String?,

    var gender:String?,

    var fatigability:Int?,

    var specification:Int?,

    var active:Int?
)
