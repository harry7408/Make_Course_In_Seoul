package com.example.whattodo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whattodo.dao.FriendDao
import com.example.whattodo.entity.Friend

@Database(entities = [Friend::class], version=1)
abstract class FriendDatabase :RoomDatabase() {
    abstract fun friendDao(): FriendDao

    companion object {
        private var INSTANCE: FriendDatabase? = null
        fun getInstance(context: Context): FriendDatabase? {
            if (INSTANCE == null) {
                synchronized(FriendDatabase::class.java) {
                    INSTANCE= Room.databaseBuilder(
                        context.applicationContext,
                        FriendDatabase::class.java,
                        "friends"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}