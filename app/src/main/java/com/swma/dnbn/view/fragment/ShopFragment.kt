package com.swma.dnbn.view.fragment

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.swma.dnbn.view.activity.ChannelActivity
import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.ShopPagerAdapter
import com.swma.dnbn.view.adapter.SlideShopAdapter
import com.swma.dnbn.model.item.ItemProduct
import kotlinx.android.synthetic.main.fragment_shop.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ShopFragment(private val product: ItemProduct) : Fragment() {

    private val job = Job()
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shop, container, false)

        // 프로그래스 바
        rootView.apply {
            progressBar_shop.visibility = View.VISIBLE
            appBar_shop.visibility = View.GONE
            shopDetailViewPager.visibility = View.GONE
        }

        // 넘어온 상품 정보
        val imageList = product.productImgList

        // Http 통신
//        val retrofit = Retrofit2Instance.getInstance()!!

        try {
            CoroutineScope(Dispatchers.IO + job).launch {
//                retrofit.getProductFromId(product.productId).execute().body().let { product ->
//                    userId = product!!.providerId

//                    retrofit.getUserFromUserId(userId).execute().body().let { user ->
                        // UI
                        CoroutineScope(Dispatchers.Main + job).launch {
                            rootView.apply {
                                textShopUserName.text = "테스트사람"
                                Picasso.get().load(R.string.test_img.toString()).into(shopUserProfile)

                                progressBar_shop.visibility = View.GONE
                                appBar_shop.visibility = View.VISIBLE
                                shopDetailViewPager.visibility = View.VISIBLE
                            }

                        }
//                    }

//                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        rootView.apply {

            // 상품 이미지 슬라이드
            shopViewPager.apply {
                adapter = SlideShopAdapter(requireActivity(), imageList)
                offscreenPageLimit = 2
                currentItem = 0
            }
            shopIndicator.setViewPager(shopViewPager)

            // 프로필 사진 원형
            shopUserProfile.apply {
                background = ShapeDrawable(OvalShape())
                clipToOutline = true
            }

            textShopTitle.text = product.productName
            textShopDescription.text = product.productDescription

            // 할인 가격 처리
            val originalPrice = product.productPrice
            val changedPrice = product.productChangedPrice

            if (changedPrice != -1) {
                textShopOriginalPrice.apply {
                    text = String.format("%,d", originalPrice)
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.dark_gray))
                    visibility = View.VISIBLE
                }
                textShopPrice.apply {
                    text = String.format("%,d", changedPrice)
                }
            } else {
                textShopPrice.text = String.format("%,d", originalPrice)
            }

            // ViewPager + TabLayout
            shopDetailViewPager.adapter = ShopPagerAdapter(requireFragmentManager(), product)
            shopTabLayout.setupWithViewPager(shopDetailViewPager)

            // 프로필 사진 클릭 (채널로 이동)
            shopUserProfile.setOnClickListener {
                val intent = Intent(requireContext(), ChannelActivity::class.java)
                intent.putExtra("userId", userId)
                requireActivity().startActivity(intent)
            }

        }


        return rootView
    }

}
