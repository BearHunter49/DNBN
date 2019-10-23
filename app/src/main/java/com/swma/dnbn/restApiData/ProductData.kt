package com.swma.dnbn.restApiData

import java.time.LocalDateTime

data class ProductData (val id: Int, val name: String, val categoryId: Int,
                        val price: Int, val changedPrice: Int, val unitInStock: Int, val description: String,
                        val providerId: Int, val deleteAt: LocalDateTime, val imageUrl: String, val detailImgUrl: String)