package com.swma.dnbn.model.item

import java.io.Serializable

data class ItemLive(
    val liveId: Int, val liveTitle: String, val liveImageUrl: String, val liveCategory: Int,
    val liveUrl: String, val liveChannelId: Int, val liveProduct: ArrayList<ItemProduct>,
    val liveViewer: Int
) : Serializable