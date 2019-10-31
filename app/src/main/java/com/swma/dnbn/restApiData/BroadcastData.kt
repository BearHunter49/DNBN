package com.swma.dnbn.restApiData

data class BroadcastData (val id: Int, val channelId: Int, val productId: Int,
                          val categoryId: Int, val title: String, val broadcastDate: String,
                          val broadcastState: Int, val thumbnailUrl: String, val url: String,
                          val mediaId: String?)