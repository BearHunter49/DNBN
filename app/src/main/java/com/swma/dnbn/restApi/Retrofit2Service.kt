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
    fun getBroadcasts(@Path("number") number: Int): Call<List<BroadcastData>>

    // Video
    @GET("/api/videos/number/{number}")
    fun getVideos(@Path("number") number: Int): Call<List<VodData>>

    @GET("/api/videos/{videoId}")
    fun getVideoFromId(@Path("videoId") videoId: Int): Call<VodData>

    @GET("/api/videos/user/{userId}")
    fun getVideosFromUserId(@Path("userId") userId: Int): Call<List<VodData>>


    // Schedules
    @GET("/api/broadcasts/schedules")
    fun getSchedules(@Query("year") year: Int,
                     @Query("month") month: Int,
                     @Query("day") day: Int): Call<List<BroadcastData>>

    // Product
    @GET("/api/products/{productId}")
    fun getProductFromId(@Path("productId") productId: Int): Call<ProductData>

    @GET("/api/products/provider/{providerId}")
    fun getProductsFromProviderId(@Path("providerId") providerId: Int): Call<List<ProductData>>

    // Channel
    @GET("/api/channels/{channelId}")
    fun getChannelFromChannelId(@Path("channelId") channelId: Int): Call<ChannelData>

    @GET("/api/channels/user/{userId}")
    fun getChannelFromUserId(@Path("userId") userId: Int): Call<ChannelData>


    // User
    @GET("/api/users/{userId}")
    fun getUserFromUserId(@Path("userId") userId: Int): Call<UserData>


}