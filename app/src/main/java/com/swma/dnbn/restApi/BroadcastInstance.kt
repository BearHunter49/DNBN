package com.swma.dnbn.restApi

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BroadcastInstance {
    private val URL = "https://1szpyu5xq7.execute-api.ap-northeast-2.amazonaws.com/media/"
    private var INSTANCE: BroadcastService? = null

    // Singleton 인스턴스
    fun getInstance(): BroadcastService?{

        if (INSTANCE == null){
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            INSTANCE = retrofit.create(BroadcastService::class.java)

        }else{
            Log.d("myTest", "Broadcast Instance 존재")
        }

        return INSTANCE
    }


}