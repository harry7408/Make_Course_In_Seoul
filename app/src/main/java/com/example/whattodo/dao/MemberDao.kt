package com.example.whattodo.dao

import androidx.room.*
import com.example.whattodo.entity.Member

@Dao
interface MemberDao {
    @Query("SELECT * FROM members")
    fun getMembers():Member

    @Query("SELECT * FROM members WHERE memberId=(:id)")
    fun getMember(id:String): Member?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(member: Member)

    @Update
    fun updateMember(member:Member)

    @Query("DELETE FROM members")
     fun delete()
}