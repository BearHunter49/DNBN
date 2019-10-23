package com.swma.dnbn.item

import java.io.Serializable

data class ItemProduct(
    val productId: Int,
    val productName: String,
    val productCategory: String,
    val productImgList: ArrayList<String>,
    val productDescription: String,
    val productPrice: Int,
    val productChangedPrice: Int,
    val productDetailImg: String,
    val productVODId: String
) : Serializable