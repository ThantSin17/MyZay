package com.stone.myzay.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stone.myzay.response.Zay


@Dao
interface ZayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(zay: Zay)

    @Query("select * from Zay")
    fun getAll():List<Zay>
}