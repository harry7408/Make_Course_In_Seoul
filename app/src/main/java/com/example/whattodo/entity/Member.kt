package com.example.whattodo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey


/* 사용자 정보 데이터 베이스를 위한 클래스 */
@Entity(tableName="members")
data class Member (
        @ColumnInfo(name="memberId")
        @PrimaryKey val id: String,
        @ColumnInfo(name="password")
        val password:String,
        @ColumnInfo(name="email")
        val email:String,
        @ColumnInfo(name="memberName")
        val memberName:String,
        @ColumnInfo(name="birthDay")
        val birthday:String,
        @ColumnInfo(name="gender")
        val gender:String,
        @ColumnInfo(name="fatigability")
        var fatigability:Int?,
        @ColumnInfo(name="specification")
        var specification:Int?,
        @ColumnInfo(name="active")
        var active:Int?,
        )