package com.stone.myzay.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ICE")
data class Ice(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo var post100:String="0",
    @ColumnInfo var post50:String="0",
    @ColumnInfo var remain100:String="0",
    @ColumnInfo var remain50:String="0",
    @ColumnInfo var price:String,
    @ColumnInfo var date:String,
    @ColumnInfo var place: String,
):Serializable