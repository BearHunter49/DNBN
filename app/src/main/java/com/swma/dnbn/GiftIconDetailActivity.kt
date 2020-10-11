package com.swma.dnbn

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.activity_gift_icon_detail.*

class GiftIconDetailActivity : AppCompatActivity() {

    private val PERMISSIONS = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_icon_detail)

        setSupportActionBar(toolbar_gifticonDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // FireBase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // 넘어온 기프티콘 정보
        val gifticon = intent.getSerializableExtra("gifticon") as ItemGiftIcon

        toolbar_gifticonDetail_title.text = gifticon.name

        Picasso.get().load(gifticon.detailImg).into(imgGifticon)
        textGifticonNumber.text = gifticon.orderNumber.toString()

        val dateList = gifticon.getDate.split("T")
        textGifticonDate.text = dateList[0]

        if (gifticon.isUse == 0) {
            textGifticonIsUse.text = "사용안함"
            lyt_isUsed.visibility = View.GONE
        } else {
            textGifticonIsUse.text = "사용함"
//            lytTab_gifticon.visibility = View.GONE
            if (gifticon.usedDate == null){
                textGifticonUsedDate.text = ""
            }else{
                textGifticonUsedDate.text = gifticon.usedDate.split("T")[0]
            }
        }

        // 저장하기 버튼
        btn_gifticon_save.setOnClickListener {
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

            // 저장 권한
            if (!hasPermission(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 100)
                return@setOnClickListener
            }

            val drawable: BitmapDrawable = imgGifticon.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            MediaStore.Images.Media.insertImage(contentResolver, bitmap, gifticon.name, gifticon.name)
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()

        }

        // 취소/환불 버튼
        btn_gifticon_cancel.setOnClickListener {
            Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show()
//            val bundle = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "bearhunter")
//            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Test")
//            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "string")
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        }


    }

    // Permit 확인
    private fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        for (permit in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permit) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    // Permit 결과
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 100) {
            // 권한 허용 시
            if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) { }
            // 권한 거부 시
            else {
                Toast.makeText(this, "저장하시려면 허용해야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
