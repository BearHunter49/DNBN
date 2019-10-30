package com.swma.dnbn.restApiData

data class GifticonData (val id: Int, val productId: Int, val isUsing: Int,
                         val issueAt: String, val usedAt: String,
                         val userId: Int, val product: ProductData, val text: String)
