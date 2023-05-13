package com.example.whattodo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whattodo.dao.MemberDao
import com.example.whattodo.entity.Member


/* 사용자 정보 저장용 데이터 앱 자체 베이스 */
@Database(entities = [Member::class],version=1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun memberDao() : MemberDao
}