package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.swma.dnbn.fragment.ShopFragment
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.row_shop_toolbar.*

class ShopActivity : AppCompatActivity() {

    override fun onStart() {
        shop_toolbar_title.text = "상품 정보"
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        // vodId 값
        val product = intent.getSerializableExtra("product") as ItemProduct


        // Toolbar
        setSupportActionBar(shop_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Fragment 생성
        supportFragmentManager.beginTransaction().add(R.id.shopContainer, ShopFragment(product), "Shop").commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
