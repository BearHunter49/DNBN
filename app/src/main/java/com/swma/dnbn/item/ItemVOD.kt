package com.swma.dnbn.item

import java.io.Serializable

data class ItemVOD(val vodId: String, val vodTitle: String, val vodImageUrl: ArrayList<String>, val vodCategory: String,
                    val vodUrl: String, val vodUserId: String, val vodProductId: String, val vodDescription: String,
                   val vodProductPrice: Int, val vodChangedPrice: Int): Serializable