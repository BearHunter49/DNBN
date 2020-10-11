package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.swma.dnbn.fragment.ShopFragment
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.row_shop_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ShopActivity : AppCompatActivity() {

    private val job = Job()

    override fun onStart() {
        shop_toolbar_title.text = "상품 정보"
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        // 넘어온 product 정보
        val product = intent.getSerializableExtra("product") as ItemProduct

        // Toolbar
        setSupportActionBar(shop_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Fragment 생성
        supportFragmentManager.beginTransaction().add(R.id.shopContainer, ShopFragment(product), "Shop").commit()

//        val retrofit = Retrofit2Instance.getInstance()!!

        // 장바구니 추가 버튼
        btn_shop_cart.setOnClickListener {
            Toast.makeText(this@ShopActivity, "현재는 지원이 안됩니다!", Toast.LENGTH_SHORT).show()
//            try {
//                CoroutineScope(Dispatchers.Default + job).launch {
//                    val response = retrofit.addCart(MyApplication.userId, product.productId, 1).execute()
//                    if (response.isSuccessful){
//                        // UI
//                        CoroutineScope(Dispatchers.Main + job).launch {
//                            Toast.makeText(this@ShopActivity, "${product.productName}\n장바구니 추가!", Toast.LENGTH_SHORT).show()
//                        }
//                    }

//                }

//            }catch (e: IOException){
//                e.printStackTrace()
//                Toast.makeText(this, "장바구니 추가 실패..", Toast.LENGTH_SHORT).show()
//            }
        }

        btn_shop_buy.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("구매되었습니다!")
            val alertDialog = builder.create()
            alertDialog.show()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
