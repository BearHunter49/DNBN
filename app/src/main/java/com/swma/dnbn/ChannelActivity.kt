package com.swma.dnbn

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import com.swma.dnbn.adapter.ChannelPagerAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.restApi.Retrofit2Instance
import kotlinx.android.synthetic.main.activity_channel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ChannelActivity : AppCompatActivity() {

    private val job = Job()
    private lateinit var vodList: ArrayList<ItemVOD>
    private lateinit var itemList: ArrayList<ItemProduct>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        progressBar_channel.visibility = View.VISIBLE
        lytConst_Channel.visibility = View.GONE

        // 툴바
        setSupportActionBar(toolbar_channel)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // 넘어온 userId로 채널 정보 가져오기
        // Http 통신
        val userId = intent.getIntExtra("userId", 0)
        vodList = arrayListOf()
        itemList = arrayListOf()
//        val retrofit = Retrofit2Instance.getInstance()!!


        CoroutineScope(Dispatchers.Default + job).launch {

//            try {
//                // VOD List
//                retrofit.getVideosFromUserId(userId).execute().body()?.forEach { video ->
//                    val productList: ArrayList<ItemProduct>
//
//                    // Product 정보
//                    retrofit.getProductFromId(video.productId).execute().body().let { product ->
//
//                        // 이미지 리스트 분리
//                        val productImgList = product!!.imageUrl.split("**") as ArrayList<String>
//
//                        productList = arrayListOf(
//                            ItemProduct(
//                                product.id,
//                                product.name,
//                                product.categoryId,
//                                productImgList,
//                                product.description,
//                                product.price,
//                                product.changedPrice,
//                                product.detailImageUrl,
//                                video.id
//                            )
//                        )
//
//                    }
//
//                    vodList.add(
//                        ItemVOD(
//                            video.id, video.name, video.thumbnailUrl, video.categoryId, video.url, video.uploaderId,
//                            productList, video.uploadAt, 100
//                        )
//                    )
//
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
            // 더미데이터
            val productImgList = arrayListOf(R.string.test_img.toString())
            val productList = arrayListOf(
                ItemProduct(
                    100,
                    "테스트1",
                    1,
                    productImgList,
                    "Test",
                    9999,
                    7000,
                    R.string.test_img.toString(),
                    100
                )
            )
            vodList.add(
                ItemVOD(
                    100, "테스트VOD", R.string.test_img.toString(), 1, R.string.test_video.toString(), 1,
                    productList, "2019-10-25 23:25:00", 999
                )
            )


//            try {
//                // Item List
//                retrofit.getProductsFromProviderId(userId).execute().body()?.forEach { product ->
//
//                    val productImgList = product.imageUrl.split("**") as ArrayList<String>
//
//                    itemList.add(
//                        ItemProduct(
//                            product.id, product.name, product.categoryId, productImgList, product.description,
//                            product.price, product.changedPrice, product.detailImageUrl, null
//                        )
//                    )
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }

            // 더미데이터
            itemList.add(
                ItemProduct(
                    100,
                    "테스트1",
                    1,
                    productImgList,
                    "Test",
                    9999,
                    7000,
                    R.string.test_img.toString(),
                    100
                )
            )


            try {
//                retrofit.getUserFromUserId(userId).execute().body().let { user ->
//                    retrofit.getChannelFromUserId(userId).execute().body().let { channel ->

                        // UI
                        CoroutineScope(Dispatchers.Main + job).launch {
                            Picasso.get().load(R.string.test_img.toString()).into(imgChannelProfile)
                            textChannelName.text = "테스트"
                            textChannelFollower.text = "123"

                            channel_viewPager.adapter = ChannelPagerAdapter(supportFragmentManager, vodList, itemList)

                            progressBar_channel.visibility = View.GONE
                            lytConst_Channel.visibility = View.VISIBLE
                        }

//                    }
//                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }


        // 사진 원형으로
        imgChannelProfile.background = ShapeDrawable(OvalShape())
        imgChannelProfile.clipToOutline = true


        lytTab_channel.setupWithViewPager(channel_viewPager)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
