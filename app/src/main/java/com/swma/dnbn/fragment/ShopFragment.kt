package com.swma.dnbn.fragment

import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.swma.dnbn.R
import com.swma.dnbn.adapter.ShopPagerAdapter
import com.swma.dnbn.adapter.SlideShopAdapter
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shop, container, false)

        // 넘어온 상품 정보
        val imageList = arrayListOf("http://img.gqkorea.co.kr/gq/2017/08/style_59896c152a6a7.jpg",
            "http://mitoshop.co.kr/web/product/medium/201802/13135_shop1_694780.jpg")
        val title = "가을 신상 맨투맨"
        val userName = "베어헌터"
        val description = "올 가을 어쩌구저쩌구~~"
        val originalPrice = 13900
        val changedPrice = 8900

        // Http 통신 상품 정보 받기
        //


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

            textShopUserName.text = userName
            textShopTitle.text = title
            textShopDescription.text = description

            // 할인 가격 처리
            if (changedPrice == -1){
                textShopOriginalPrice.apply {
                    text = String.format("%,d", originalPrice)
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.gray))
                    visibility = View.VISIBLE
                }
                textShopPrice.apply {
                    text = String.format("%,d", changedPrice)
                }
            }else{
                textShopPrice.text = String.format("%,d", originalPrice)
            }

            // ViewPager + TabLayout
            shopDetailViewPager.adapter = ShopPagerAdapter(requireActivity().supportFragmentManager)
            shopTabLayout.setupWithViewPager(shopDetailViewPager)





        }


        return rootView
    }

}
