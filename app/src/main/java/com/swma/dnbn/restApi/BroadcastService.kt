package com.swma.dnbn.restApi

import com.swma.dnbn.restApiData.MediaData
import com.swma.dnbn.restApiData.MediaStopData
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BroadcastService {

    @FormUrlEncoded
    @POST("bylive")
    fun onBroadcast(@FieldMap param: HashMap<String, String>): Call<MediaData>

    @FormUrlEncoded
    @POST("bylive")
    fun stopBroadcast(@FieldMap param: HashMap<String, String>): Call<MediaStopData>


}