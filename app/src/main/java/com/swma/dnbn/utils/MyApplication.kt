package com.swma.dnbn.utils

import android.app.Application
import com.swma.dnbn.model.OutputStartModel

class MyApplication : Application(){
    companion object{
        const val userId = 1
        var userName: String = "BearHunter49"
        var mediaOutput: OutputStartModel? = null
    }
}