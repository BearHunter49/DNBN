package com.swma.dnbn.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.journeyapps.barcodescanner.CaptureManager
import com.swma.dnbn.R
import kotlinx.android.synthetic.main.activity_barcode.*

class BarcodeActivity : AppCompatActivity() {

    lateinit var manager: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        manager = CaptureManager(this, decor_barcodeView)
        manager.apply {
            initializeFromIntent(intent, savedInstanceState)
            decode()
        }

    }

    override fun onResume() {
        super.onResume()
        manager.onResume()
    }

    override fun onPause() {
        super.onPause()
        manager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.onDestroy()
    }



}
