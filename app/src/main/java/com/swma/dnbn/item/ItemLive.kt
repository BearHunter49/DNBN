package com.swma.dnbn.item

import java.io.Serializable

data class ItemLive(
    val liveId: Int, val liveTitle: String, val liveImageUrl: String, val liveCategory: String,
    val liveUrl: String, val liveUserId: Int, val liveProduct: ArrayList<ItemProduct>,
    val liveViewer: Int
) : Serializable