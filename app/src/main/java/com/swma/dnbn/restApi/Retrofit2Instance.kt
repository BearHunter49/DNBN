package com.swma.dnbn.restApi

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit2Instance{
    private val URL = "http://jsonplaceholder.typicode.com"
    private var INSTANCE: Retrofit2Service? = null

    // Singleton 인스턴스
    fun getInstance(): Retrofit2Service? {
        if (INSTANCE == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            INSTANCE = retrofit.create(Retrofit2Service::class.java)
        }
        else{
            Log.d("myTest", "INSTANCE null 아님")
        }

        return INSTANCE
    }

}