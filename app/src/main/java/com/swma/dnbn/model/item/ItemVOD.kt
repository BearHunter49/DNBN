package com.swma.dnbn.model.item

import java.io.Serializable

data class ItemVOD(
    val vodId: Int, val vodTitle: String, val vodThumbnailUrl: String, val vodCategory: Int,
    val vodUrl: String, val vodUserId: Int, val vodProduct: ArrayList<ItemProduct>, val vodDate: String, val vodViewer: Int
) : Serializable