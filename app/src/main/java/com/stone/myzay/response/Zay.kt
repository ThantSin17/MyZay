package com.stone.myzay.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Zay")
data class Zay (
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = "title") var title:String,
    @ColumnInfo(name = "price") var price:String,
    @ColumnInfo(name = "img") var img:String,
)
