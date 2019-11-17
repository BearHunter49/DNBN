package com.swma.dnbn.util

import android.app.Application
import com.swma.dnbn.model.OutputStartModel

class MyApplication : Application(){
    companion object{
        const val userId = 1
        var userName: String? = null
        var mediaOutput: OutputStartModel? = null
    }
}