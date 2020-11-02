package com.swma.dnbn.model.dummyData

import com.swma.dnbn.model.item.ItemGiftIcon

object GiftIconDummy{
    val noUseGiftIcon = arrayListOf(
        ItemGiftIcon(
            "Good Meat",
            "https://cdn.pixabay.com/photo/2017/03/23/19/57/asparagus-2169305_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/03/23/19/57/asparagus-2169305_960_720.jpg",
            100,
            "2019-10-25T23:25:00",
            0,
            ""
        )
    )

    val usedGiftIcon = arrayListOf(
        ItemGiftIcon(
            "Good Pasta",
            "https://cdn.pixabay.com/photo/2018/07/18/19/12/spaghetti-3547078_960_720.jpg",
            "https://cdn.pixabay.com/photo/2018/07/18/19/12/spaghetti-3547078_960_720.jpg",
            101,
            "2019-10-25T23:25:00",
            1,
            "2019-11-30T23:25:00"
        )
    )
}
