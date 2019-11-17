package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.swma.dnbn.adapter.GiftIconPagerAdapter
import com.swma.dnbn.item.ItemGiftIcon
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.activity_gifticon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class GiftIconActivity : AppCompatActivity() {

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon)

        setSupportActionBar(toolbar_gifticon)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_gifticon_title.text = "기프티콘함"

        val noUseList = arrayListOf<ItemGiftIcon>()
        val usedList = arrayListOf<ItemGiftIcon>()

        // Http 통신
        // 기프티콘 함 정보 얻어오기
        val retrofit = Retrofit2Instance.getInstance()!!

        CoroutineScope(Dispatchers.Default + job).launch {
            try {
                retrofit.getGifticonFromUserId(MyApplication.userId).execute().body()?.forEach { gifticon ->

                    retrofit.getProductFromId(gifticon.productId).execute().body().let { product ->
                        val productImgList = product!!.imageUrl.split("**")

                        if (gifticon.isUsing == 0) {
                            noUseList.add(
                                ItemGiftIcon(
                                    product.name,
                                    productImgList[0],
                                    gifticon.image,
                                    gifticon.id,
                                    gifticon.issueAt,
                                    gifticon.isUsing,
                                    gifticon.usedAt
                                )
                            )
                        } else {
                            usedList.add(
                                ItemGiftIcon(
                                    product.name,
                                    productImgList[0],
                                    gifticon.image,
                                    gifticon.id,
                                    gifticon.issueAt,
                                    gifticon.isUsing,
                                    gifticon.usedAt
                                )
                            )
                        }

                    }


                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {
                gifticon_viewPager.adapter = GiftIconPagerAdapter(supportFragmentManager, noUseList, usedList)
            }
        }



        gifticon_tabLayout.setupWithViewPager(gifticon_viewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
