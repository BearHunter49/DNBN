package com.swma.dnbn.item

import java.io.Serializable

data class ItemGiftIcon(val name: String, val img: String,
                        val orderNumber: Int, val getDate: String, val isUse: Int, val usedDate: String?): Serializable