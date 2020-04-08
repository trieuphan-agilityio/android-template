package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.core.data.entities.User

@Dao
interface UserDao : BaseDao<User> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(user: User)
}