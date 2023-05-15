package com.example.whattodo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whattodo.dao.MemberDao
import com.example.whattodo.datas.User
import com.example.whattodo.entity.Member


/* 사용자 정보 저장용 데이터 앱 자체 베이스 */
@Database(entities = [Member::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE= Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "members"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}