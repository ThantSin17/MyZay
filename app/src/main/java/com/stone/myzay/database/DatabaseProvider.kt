package com.stone.myzay.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database:IceDatabase?=null

    fun instance(context: Context):IceDatabase{
        if (database==null){
            database= Room.databaseBuilder(context,IceDatabase::class.java,"ICEDB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return database!!
    }
}