package com.swma.dnbn.view.activity

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import com.swma.dnbn.view.adapter.ChannelPagerAdapter
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.model.item.ItemVOD
import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.ProductDummy
import com.swma.dnbn.model.dummyData.VodDummy
import kotlinx.android.synthetic.main.activity_channel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ChannelActivity : AppCompatActivity() {
    private lateinit var vodList: ArrayList<ItemVOD>
    private lateinit var itemList: ArrayList<ItemProduct>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

//        val userId = intent.getIntExtra("userId", 0)

        visibleProgressBar(true)

        // App Bar
        setSupportActionBar(toolbar_channel)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ---- Dummy -----
        vodList = VodDummy.vodData
        itemList = ProductDummy.productFoodData
        val profileImg = getString(R.string.test_profile_img)
        val name = getString(R.string.test_channel)
        val follower = getString(R.string.test_channel_follower)
        // ----------------

        bindChannelData(profileImg, name, follower)
        makeProfileCircle()

        visibleProgressBar(false)
    }

    private fun makeProfileCircle() {
        imgChannelProfile.background = ShapeDrawable(OvalShape())
        imgChannelProfile.clipToOutline = true
    }

    /**
     * 채널 데이터 View들에게 Bind
     */
    private fun bindChannelData(img: String, name: String, follower: String) {
        Picasso.get().load(img).into(imgChannelProfile)

        textChannelName.text = name
        textChannelFollower.text = follower

        channel_viewPager.adapter = ChannelPagerAdapter(supportFragmentManager, vodList, itemList)
        lytTab_channel.setupWithViewPager(channel_viewPager)
    }

    private fun visibleProgressBar(valid: Boolean) {
        when (valid) {
            true -> {
                progressBar_channel.visibility = View.VISIBLE
                lytConst_Channel.visibility = View.GONE
            }
            else -> {
                progressBar_channel.visibility = View.GONE
                lytConst_Channel.visibility = View.VISIBLE
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
