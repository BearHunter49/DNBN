package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_barcode_result.*

class BarcodeResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_result)

        val resultCode = intent.getStringExtra("code")

        // Http 통신으로 상품 확인하기
        // 맞으면 gifticon 정보 받아오고
        // 아니면 틀렸다고 하기

        textCode.text = resultCode

    }
}
