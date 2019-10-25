package com.swma.dnbn

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.swma.dnbn.adapter.ChannelPagerAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        // 툴바
        setSupportActionBar(toolbar_channel)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 넘어온 userId로 ItemUser 정보 얻어오기
        // Http 통신
//        val userId = intent.getStringExtra("userId")

//        textChannelName.text = ""
//        textChannelFollower.text = ""
//        Picasso.get().load("").into(imgChannelProfile)

        // Http 통신으로 해당 유저의 영상, 상품 얻어오기
        val vodList = arrayListOf(
            ItemVOD(
                1, "VOD 반려동물", "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg", 1, "",
                100, arrayListOf(
                    ItemProduct(
                        1,
                        "상품이름1",
                        "Pet",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Des",
                        13000,
                        8900,
                        "",
                        3
                    )
                ), "2019-10-10", 20
            )
        )

        val itemList = arrayListOf(
            ItemProduct(
                1,
                "신상 맨투맨1",
                "Fashion",
                arrayListOf(
                    "http://takeastreet.com/web/product/big/201810/d6e010bb1e5c4b6981a7243d98f8e3c9.jpg",
                    "http://mitoshop.co.kr/web/product/medium/201802/13135_shop1_694780.jpg"
                ),
                "Test Description1",
                15000,
                13900,
                "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                1
            )
        )

        // 사진 원형으로
        imgChannelProfile.background = ShapeDrawable(OvalShape())
        imgChannelProfile.clipToOutline = true

        channel_viewPager.adapter = ChannelPagerAdapter(supportFragmentManager, vodList, itemList)
        lytTab_channel.setupWithViewPager(channel_viewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
