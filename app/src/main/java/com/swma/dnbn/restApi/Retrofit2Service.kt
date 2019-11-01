package com.swma.dnbn.restApi

import com.swma.dnbn.restApiData.*
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate

interface Retrofit2Service{

    // BroadCast
    @GET("/api/broadcasts/number/{number}")
    fun getBroadcasts(@Path("number") number: Int): Call<List<BroadcastData>>

    @GET("/api/broadcasts/{broadCastId}")
    fun getBroadcastFromId(@Path("broadCastId") broadcastId: Int): Call<BroadcastData>

    @GET("/api/broadcasts/channel/{channelId}")
    fun getBroadcastFromChannelId(@Path("channelId") channelId: Int): Call<List<BroadcastData>>

    @GET("/api/broadcasts/category/{categoryId}")
    fun getBroadcastsFromCategoryId(@Path("categoryId") categoryId: Int): Call<List<BroadcastData>>

    @GET("/api/broadcasts/schedules")
    fun getSchedules(@Query("year") year: Int,
                     @Query("month") month: Int,
                     @Query("day") day: Int): Call<List<BroadcastData>>

    @PUT("/api/broadcasts/on")
    fun onBroadcast(@Query("broadcastId") broadcastId: Int,
                    @Query("url") url: String,
                    @Query("mediaId") mediaId: Int): Call<Void>

    @PUT("/api/broadcasts/off/{broadcastId}")
    fun offBroadcast(@Path("broadcastId") broadcastId: Int): Call<Void>


    // Video
    @GET("/api/videos/number/{number}")
    fun getVideos(@Path("number") number: Int): Call<List<VodData>>

    @GET("/api/videos/{videoId}")
    fun getVideoFromId(@Path("videoId") videoId: Int): Call<VodData>

    @GET("/api/videos/user/{userId}")
    fun getVideosFromUserId(@Path("userId") userId: Int): Call<List<VodData>>

    @GET("/api/videos/category/{categoryId}")
    fun getVideosFromCategoryId(@Path("categoryId") categoryId: Int): Call<List<VodData>>

    @GET("/api/videos/product/{productId}")
    fun getVideosFromProductId(@Path("productId") productId: Int): Call<List<VodData>>

    @POST("/api/videos")
    fun addVideo(@Query("productId") productId: Int,
                 @Query("name") name: String,
                 @Query("uploaderId") uploaderId: Int,
                 @Query("url") url: String,
                 @Query("categoryId") categoryId: Int,
                 @Query("description") description: String,
                 @Query("thumbnailUrl") thumbnailUrl: String): Call<VodData>


    // Product
    @GET("/api/products/{productId}")
    fun getProductFromId(@Path("productId") productId: Int): Call<ProductData>

    @GET("/api/products/provider/{providerId}")
    fun getProductsFromProviderId(@Path("providerId") providerId: Int): Call<List<ProductData>>

    @GET("/api/products/category/{categoryId}")
    fun getProductsFromCategoryId(@Path("categoryId") categoryId: Int): Call<List<ProductData>>



    // Channel
    @GET("/api/channels/{channelId}")
    fun getChannelFromChannelId(@Path("channelId") channelId: Int): Call<ChannelData>

    @GET("/api/channels/user/{userId}")
    fun getChannelFromUserId(@Path("userId") userId: Int): Call<ChannelData>

    // User
    @GET("/api/users/{userId}")
    fun getUserFromUserId(@Path("userId") userId: Int): Call<UserData>

    @PUT("/api/users/")
    fun changeUserLocation(@Query("city") city: String,
                           @Query("gu") gu: String,
                           @Query("dong") dong: String,
                           @Query("detail") detail: String,
                           @Query("userId") userId: Int): Call<UserData>

    // Cart
    @GET("/api/carts/{userId}")
    fun getCartFromUserId(@Path("userId") userId: Int): Call<CartData>

    @POST("/api/carts/adding")
    fun addCart(@Query("parameters") userId: Int,
                @Query("parameters") productId: Int,
                @Query("parameters") productQ: Int): Call<CartData>

    // GiftIcon
    @GET("/api/gifticons/user/{userId}")
    fun getGifticonFromUserId(@Path("userId") userId: Int): Call<List<GifticonData>>

    @GET("/api/gifticons/{gifticonId}")
    fun getGifticonFromId(@Path("gifticonId") gifticonId: Int): Call<GifticonData>

    @PUT("/api/gifticons/{gifticonId}")
    fun useGifticon(@Path("gifticonId") gifticonId: Int): Call<GifticonData>


}