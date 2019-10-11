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

        textCode.text = resultCode

    }
}
