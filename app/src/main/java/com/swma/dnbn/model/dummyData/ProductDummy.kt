package com.swma.dnbn.model.dummyData

import com.swma.dnbn.model.item.ItemProduct

object ProductDummy {
    private val productImgFoods = arrayListOf(
        "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_960_720.jpg",
        "https://cdn.pixabay.com/photo/2016/04/15/08/04/strawberries-1330459_960_720.jpg"
    )
    private val productImgClothes = arrayListOf(
        "https://cdn.pixabay.com/photo/2016/03/25/09/04/t-shirt-1278404_960_720.jpg",
        "https://cdn.pixabay.com/photo/2014/11/09/19/17/mens-shirt-524022_960_720.jpg"
    )
    val productFoodData = arrayListOf(
        ItemProduct(
            100,
            "Fruits",
            1,
            this.productImgFoods,
            "Delicious Fruits!",
            9999,
            7000,
            "https://cdn.pixabay.com/photo/2017/05/11/19/44/fruit-2305192_960_720.jpg",
            100
        )
    )
    val productClothData = arrayListOf(
        ItemProduct(
            101,
            "Shirts",
            7,
            this.productImgClothes,
            "Fantastic Clothes!",
            19999,
            10000,
            "https://cdn.pixabay.com/photo/2015/01/21/17/22/shopping-606993_960_720.jpg",
            100
        )
    )
}