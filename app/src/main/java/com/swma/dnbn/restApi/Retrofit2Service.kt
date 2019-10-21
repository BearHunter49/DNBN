package com.swma.dnbn.restApi

import retrofit2.Call
import retrofit2.http.*

interface Retrofit2Service{
    @GET("/posts/{userId}")
    fun getData(@Path("userId") userId: String): Call<TestData>

    @FormUrlEncoded
    @POST("/posts")
    fun postData(@FieldMap param: HashMap<String, Any>): Call<TestData>
}