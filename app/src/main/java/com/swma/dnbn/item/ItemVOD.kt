package com.swma.dnbn.item

import java.io.Serializable

data class ItemVOD(val vodId: String, val vodTitle: String, val vodImageUrl: String, val vodCategory: String,
                    val vodUrl: String, val vodUserId: String, val vodDescription: String, val vodDetailImg: String,
                   val vodProduct: ArrayList<ItemProduct>): Serializable