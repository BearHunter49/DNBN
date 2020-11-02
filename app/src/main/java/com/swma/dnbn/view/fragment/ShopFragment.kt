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
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.fragment_shop.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.ArrayList

class ShopFragment(private val product: ItemProduct) : Fragment() {

    private val job = Job()
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visibleProgressBar(true)
        makeProfileCircle()

        bindProductData()

        // 상단 이미지 슬라이드
        setAdapterViewPager(product.productImgList)

        // 하단 상세 페이지 탭
        setAdapterTabLayout()

        visibleProgressBar(false)

        
        // 프로필 사진 클릭 (채널로 이동)
        shopUserProfile.setOnClickListener {
            val intent = Intent(requireContext(), ChannelActivity::class.java)
            intent.putExtra("userId", userId)
            requireActivity().startActivity(intent)
        }
    }

    private fun setAdapterTabLayout() {
        shopDetailViewPager.adapter = ShopPagerAdapter(requireFragmentManager(), product)
        shopTabLayout.setupWithViewPager(shopDetailViewPager)
    }

    private fun setAdapterViewPager(productImgList: ArrayList<String>) {
        shopViewPager.apply {
            adapter = SlideShopAdapter(requireActivity(), productImgList)
            offscreenPageLimit = 2
            currentItem = 0
        }
        shopIndicator.setViewPager(shopViewPager)
    }

    private fun bindProductData() {
        // Dummy Data
        val shopUserName = getString(R.string.test_channel)
        val profileImg = getString(R.string.test_profile_img)


        textShopUserName.text = shopUserName
        Picasso.get().load(profileImg).into(shopUserProfile)
        textShopTitle.text = product.productName
        textShopDescription.text = product.productDescription

        // 할인 가격 처리
        val originalPrice = product.productPrice
        val changedPrice = product.productChangedPrice
        setChangedPrice(originalPrice, changedPrice)
    }

    private fun setChangedPrice(originalPrice: Int, changedPrice: Int) {
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
    }

    private fun makeProfileCircle() {
        shopUserProfile.apply {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true
        }
    }

    private fun visibleProgressBar(valid: Boolean){
        when (valid){
            true -> {
                progressBar_shop.visibility = View.VISIBLE
                appBar_shop.visibility = View.GONE
                shopDetailViewPager.visibility = View.GONE
            }
            else -> {
                progressBar_shop.visibility = View.GONE
                appBar_shop.visibility = View.VISIBLE
                shopDetailViewPager.visibility = View.VISIBLE
            }
        }
    }

}
