package com.swma.dnbn.restApiData

import java.time.LocalDateTime

data class BroadcastData (val id: Int, val channelId: Int, val productId: Int,
                          val categoryId: Int, val title: String, val broadcastDate: String,
                          val broadcastState: Int, val thumbnailUrl: String, val url: String)