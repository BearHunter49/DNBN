package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swma.dnbn.fragment.ShopFragment
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        // Intent 정보 받아오기

        // Fragment 생성성
       supportFragmentManager.beginTransaction().add(R.id.shopContainer, ShopFragment(), "Shop").commit()

    }
}
