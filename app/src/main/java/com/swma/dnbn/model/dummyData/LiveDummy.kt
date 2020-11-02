package com.swma.dnbn.model.dummyData

import com.swma.dnbn.model.item.ItemLive

object LiveDummy {
    val liveData = arrayListOf(
        ItemLive(
            100,
            "Fruits Live",
            "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_960_720.jpg",
            1,
            "https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8",
            90,
            ProductDummy.productFoodData,
            125
        ),
        ItemLive(
            101,
            "Clothes Live",
            "https://cdn.pixabay.com/photo/2016/03/25/09/04/t-shirt-1278404_960_720.jpg",
            7,
            "https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8",
            91,
            ProductDummy.productClothData,
            125
        )
    )

}