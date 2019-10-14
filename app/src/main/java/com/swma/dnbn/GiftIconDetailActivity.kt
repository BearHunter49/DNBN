package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.activity_gift_icon_detail.*

class GiftIconDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_icon_detail)

        setSupportActionBar(toolbar_gifticonDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 넘어온 기프티콘 정보
        val gifticon = intent.getSerializableExtra("gifticon") as ItemGiftIcon

        toolbar_gifticonDetail_title.text = gifticon.name

        Picasso.get().load(gifticon.img).into(imgGifticon)
        Picasso.get().load(gifticon.barcodeImg).into(imgGifticonBarcode)

        textGifticonNumber.text = gifticon.orderNumber
        textGifticonBrand.text = gifticon.brand
        textGifticonDate.text = gifticon.getDate

        if (gifticon.isUse == 0){
            textGifticonIsUse.text = "사용안함"
        }else{
            textGifticonIsUse.text = "사용함"
        }

        // 선물하기 버튼
        btn_gifticon_present.setOnClickListener {

        }

        // 취소/환불 버튼
        btn_gifticon_cancel.setOnClickListener {

        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
