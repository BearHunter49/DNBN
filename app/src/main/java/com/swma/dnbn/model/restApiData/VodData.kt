package com.swma.dnbn.model.restApiData

data class VodData (val id: Int, val productId: Int, val name: String,
                    val uploaderId: Int, val url: String, val categoryId: Int,
                    val description: String, val thumbnailUrl: String,
                    val uploadAt: String)
