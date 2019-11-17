package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.swma.dnbn.restApi.Retrofit2Instance
import kotlinx.android.synthetic.main.activity_barcode_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BarcodeResultActivity : AppCompatActivity() {

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_result)

        val resultCode = intent.getStringExtra("code")!!
        textCode.text = resultCode

        // Http 통신으로 상품 확인하기
        val retrofit = Retrofit2Instance.getInstance()!!

        val gifticonId = resultCode.toIntOrNull()

        // 기프티콘 형식이 맞다면
        if (gifticonId != null){
            CoroutineScope(Dispatchers.Default + job).launch {
                val response = retrofit.getGifticonFromId(gifticonId).execute()

                // 해당 기프티콘 존재
                if (response.isSuccessful){
                    response.body().let { gifticon ->

                        // 미사용 제품일 시
                        if (gifticon!!.isUsing == 0){
                            retrofit.useGifticon(gifticonId).execute()

                            // UI
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@BarcodeResultActivity, "결제 되었습니다!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        // 이미 사용한 제품일 시
                        else{
                            // UI
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@BarcodeResultActivity, "이미 사용한 제품입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        // UI
                        CoroutineScope(Dispatchers.Main).launch {
                            textGifticonNumber.text = gifticon.id.toString()
                            textGifticonDate.text = gifticon.issueAt.split("T")[0]
                            Picasso.get().load(gifticon.image).into(barcode_gifticon_img)
                            if (gifticon.usedAt == null){
                                textGifticonUsedDate.text = ""
                            }else{
                                textGifticonUsedDate.text = gifticon.usedAt.split("T")[0]
                            }
                        }

                    }

                }else{
                    // UI
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@BarcodeResultActivity, "그러한 기프티콘은 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                        barcode_gifticon_img.visibility = View.GONE
                        lyt_barcode_result.visibility = View.GONE
                    }
                }

            }
        }
        // id 형태가 아니라면
        else{
            Toast.makeText(this, "기프티콘 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            barcode_gifticon_img.visibility = View.GONE
            lyt_barcode_result.visibility = View.GONE
        }



    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
