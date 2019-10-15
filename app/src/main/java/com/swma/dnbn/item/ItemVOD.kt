package com.swma.dnbn.item

import java.io.Serializable

data class ItemVOD(
    val vodId: String, val vodTitle: String, val vodThumbnailUrl: String, val vodCategory: String,
    val vodUrl: String, val vodUserId: String, val vodProduct: ArrayList<ItemProduct>, val vodDate: String, val vodViewer: Int
) : Serializable