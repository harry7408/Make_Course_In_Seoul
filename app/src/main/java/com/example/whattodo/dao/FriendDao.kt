package com.example.whattodo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.whattodo.entity.Friend
import com.example.whattodo.entity.Member
import retrofit2.http.DELETE

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends")
    fun getMembers():List<Friend>

    @Delete
    fun delete(friendId:Friend)
}