package com.swma.dnbn.restApi

import com.swma.dnbn.restApiData.*
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate

interface Retrofit2Service{
//    @GET("/posts/{userId}")
//    fun getData(@Path("userId") userId: String): Call<TestData>
//
//    @FormUrlEncoded
//    @POST("/posts")
//    fun postData(@FieldMap param: HashMap<String, Any>): Call<TestData>

    // BroadCast
    @GET("/api/broadcasts/number/{number}")
    fun getBroadcasts(@Path("number") number: String): Call<List<BroadcastData>>

    // Video
    @GET("/api/videos/number/{number}")
    fun getVideos(@Path("number") number: String): Call<List<VodData>>

    // Schedules
    @GET("/api/broadcasts/schedules/{year}/{month}/{day}")
    fun getSchedules(@Path("year") year: Int,
                     @Path("month") month: Int,
                     @Path("day") day: Int): Call<List<BroadcastData>>

    // Product
    @GET("/api/products/{productId}")
    fun getProductFromId(@Path("productId") productId: Int): Call<ProductData>

    // Channel
    @GET("/api/channels/{channelId}")
    fun getChannelFromChannelId(@Path("channelId") channelId: Int): Call<ChannelData>

    // User
    @GET("/api/users/{userId}")
    fun getUserFromUserId(@Path("userId") userId: Int): Call<UserData>


}