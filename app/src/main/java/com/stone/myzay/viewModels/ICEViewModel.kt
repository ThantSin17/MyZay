package com.stone.myzay.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ICEViewModel : ViewModel() {
     var sell100:Int=0
     var sell50:Int=0
    fun add(): Int {
        return sell100*100 + sell50*50
    }

}