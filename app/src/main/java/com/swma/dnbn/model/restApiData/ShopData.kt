package com.swma.dnbn.model.restApiData

data class ShopData (val id: Int, val name: String, val providerId: Int,
                     val uri: String, val city: String, val gu: String,
                     val dong: String, val detail: String, val followerNumber: Int,
                     val categoryId: Int)

// User 정보도 넘어오나?