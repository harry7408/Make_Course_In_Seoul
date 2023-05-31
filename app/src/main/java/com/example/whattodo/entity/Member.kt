package com.example.whattodo.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "members")
data class Member(
    /*var uuid:String,*/
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
) : Parcelable
