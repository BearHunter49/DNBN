package com.swma.dnbn.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.swma.dnbn.view.adapter.GiftIconPagerAdapter
import com.swma.dnbn.model.item.ItemGiftIcon
import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.GiftIconDummy
import kotlinx.android.synthetic.main.activity_gifticon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

class GiftIconActivity : AppCompatActivity() {

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifticon)

        setSupportActionBar(toolbar_gifticon)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Dummy Data
        val noUseList = GiftIconDummy.noUseGiftIcon
        val usedList = GiftIconDummy.usedGiftIcon

        bindGiftIconData(noUseList, usedList)
    }

    /**
     * GiftIcon View에 넣기
     */
    private fun bindGiftIconData(
        noUseList: ArrayList<ItemGiftIcon>,
        usedList: ArrayList<ItemGiftIcon>
    ) {
        gifticon_viewPager.adapter =
            GiftIconPagerAdapter(supportFragmentManager, noUseList, usedList)
        gifticon_tabLayout.setupWithViewPager(gifticon_viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
