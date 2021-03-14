package com.stone.myzay.dao

import androidx.room.*
import com.stone.myzay.response.Ice

@Dao
interface IceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addICE(ice:Ice)

    @Query("select * from ICE where date=:date")
    fun getICE(date:String):Ice?

    @Update
    fun updateIce(ice: Ice)

}