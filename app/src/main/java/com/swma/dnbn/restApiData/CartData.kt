package com.swma.dnbn.restApiData

data class CartData (val id: Int, val product01Id: Int, val product01Quantity: Int, val product02Id: Int,
                     val product02Quantity: Int, val price: Int, val product01: ProductData?, val product02: ProductData?)
