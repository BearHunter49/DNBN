package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.swma.dnbn.adapter.GiftIconPagerAdapter
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.activity_gifticon.*

class GiftIconActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon)

        setSupportActionBar(toolbar_gifticon)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_gifticon_title.text = "기프티콘함"

        // Http 통신
        // 기프티콘 함 정보 얻어오기
        val noUseList = arrayListOf(
            ItemGiftIcon(
                "츀힌",
                "BBQ",
                "https://img.bbq.co.kr:449/uploads/bbq_d/thumbnail/gold_olive_list_0423.jpg",
                "https://www.cognex.com/BarcodeGenerator/Content/images/isbn.png",
                "1234567",
                "2019-10-14",
                0
            )
        )

        val usedList = arrayListOf(
            ItemGiftIcon(
                "핏자",
                "도민호",
                "https://cdn.dominos.co.kr/admin/upload/goods/20180827_ZIna5r8b.jpg",
                "https://www.cognex.com/BarcodeGenerator/Content/images/isbn.png",
                "9876543",
                "2019-10-12",
                1
            )
        )

        gifticon_viewPager.adapter = GiftIconPagerAdapter(supportFragmentManager, noUseList, usedList)
        gifticon_tabLayout.setupWithViewPager(gifticon_viewPager)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
