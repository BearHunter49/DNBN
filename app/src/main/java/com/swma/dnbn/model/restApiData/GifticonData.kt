package com.swma.dnbn.model.restApiData

data class GifticonData (val id: Int, val productId: Int, val isUsing: Int,
                         val issueAt: String, val usedAt: String?,
                         val userId: Int, val text: String?, val image: String)
