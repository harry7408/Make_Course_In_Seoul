package com.example.whattodo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whattodo.dao.MemberDao
import com.example.whattodo.entity.Member

@Database(entities = [Member::class],version=1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun memberDao() : MemberDao
}