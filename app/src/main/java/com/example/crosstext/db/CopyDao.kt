package com.example.crosstext.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CopyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CopyData>)

    @Query("SELECT * FROM crossText")
    fun getAllData():LiveData<List<CopyData>>

}