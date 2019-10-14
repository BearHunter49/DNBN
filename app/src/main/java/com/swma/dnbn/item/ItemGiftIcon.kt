package com.swma.dnbn.item

import java.io.Serializable

data class ItemGiftIcon(val name: String, val brand: String, val img: String, val barcodeImg: String,
                        val orderNumber: String, val getDate: String, val isUse: Int): Serializable