package com.swma.dnbn.item

import java.io.Serializable

data class ItemLive(val liveId: String, val liveTitle: String, val liveImageUrl: String, val liveCategory: String,
                    val liveUrl: String, val liveUserId: String, val liveProductId: String, val liveProductPrice: Int,
                    val liveChangedPrice: Int, val liveViewer: Int): Serializable