package com.example.core.data.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    /**
     * Insert an object in the database
     *
     * @param t the object to be inserted.
     * @return number row insert success
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param t the object to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(t: T): Int

    /**
     * Update an object from the database.
     *
     * @param t the object to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(t: List<T>): Int

    /**
     * Delete an object from the database
     *
     * @param t the object to be deleted
     */
    @Delete
    fun delete(t: T): Int

    /**
     * Delete list object from database
     *
     * @param t the list object to be deleted
     */
    @Delete
    fun delete(t: List<T>): Int
}