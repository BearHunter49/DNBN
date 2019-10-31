package com.swma.dnbn

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.activity_gift_icon_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

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
        textGifticonNumber.text = gifticon.orderNumber.toString()

        val dateList = gifticon.getDate.split("T")
        textGifticonDate.text = dateList[0]

        if (gifticon.isUse == 0) {
            textGifticonIsUse.text = "사용안함"
        } else {
            textGifticonIsUse.text = "사용함"
        }

        // 선물하기 버튼
        btn_gifticon_present.setOnClickListener {
            // sms
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.putExtra("sms_body", message)
//            intent.type = "vnd.android-dir/mms-sms"
//            startActivity(intent)

            // kakao talk
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.type = "image/png"
//            intent.putExtra(
//                Intent.EXTRA_STREAM,
//                Uri.parse("https://www.mpps.co.kr/kfcs_api_img/KFCS/goods/DL_2172965_20181218164144426.png")
//            )
////            intent.`package` = "com.kakao.talk"

//            startActivity(Intent.createChooser(intent, "뭐 선택..."))
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
