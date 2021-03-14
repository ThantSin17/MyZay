package com.stone.myzay.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stone.myzay.dao.IceDao
import com.stone.myzay.dao.ZayDao
import com.stone.myzay.response.Ice
import com.stone.myzay.response.Zay

@Database(entities = [Ice::class,Zay::class],version = 1,exportSchema = false)
abstract class IceDatabase :RoomDatabase(){
    abstract fun iceDao():IceDao

    abstract fun zayDao():ZayDao
}