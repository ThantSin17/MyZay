package com.stone.myzay.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    @JvmStatic
    fun getCurrentDate() :String{
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyy") //or use getDateInstance()
        return formatter.format(date)
    }
    @JvmStatic
    fun getTotal(ice100:String,ice50:String):String{
        return  (ice100.toInt()*100 + ice50.toInt()*50).toString()
    }
    @JvmStatic
    @BindingAdapter("addImage")
    fun addImage(image:ImageView,string: String){
//        var temp=Base64.decode(string,Base64.DEFAULT)
//        var bitmap=BitmapFactory.decodeByteArray(temp,0,temp.size)
//        image.setImageBitmap(bitmap)
        val bitmap= AppUtils.stringToBitmap(string)
        image.setImageBitmap(bitmap)
    }
    @JvmStatic
    fun bitmapToString(bitmap: Bitmap) :String{
        val baos=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val b=baos.toByteArray()
        return Base64.encodeToString(b,Base64.DEFAULT)
    }

    @JvmStatic
    fun stringToBitmap(str: String):Bitmap{
        val b=Base64.decode(str,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(b,0,b.size)
    }
}