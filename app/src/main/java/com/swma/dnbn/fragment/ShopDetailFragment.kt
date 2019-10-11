package com.swma.dnbn.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import com.swma.dnbn.R
import com.swma.dnbn.VODWatchActivity
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_shop_detail.view.*

class ShopDetailFragment(private val product: ItemProduct) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shop_detail, container, false)

        // 넘어온 상품 정보
//        val vodId = product.productVODId
        val imgDetail = product.productDetailImg

        // Http 통신
        // vodId를 통해 VOD 정보 얻기
        val vod = ItemVOD(
            "1",
            "테스트 VOD 타이틀",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhf72a9zX1BH_ZsshHeDiExncM3DwTXKoCNdDiYbNcMu0l84hHUw",
            "Fashion",
            "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8",
            "베어헌터",
            arrayListOf(
                ItemProduct(
                    "1",
                    "신상 맨투맨2",
                    "Fashion",
                    arrayListOf(
                        "http://image.musinsa.com/images/goods_img/20170912/630262/630262_3_500.jpg",
                        "https://m.mutnam.com/web/product/big/201901/03df533b5a420017b3abdbf7420ef919.jpg"
                    ),
                    "Test Description2",
                    13000,
                    11800,
                    "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                    "1"
                ),
                ItemProduct(
                    "1",
                    "신상 맨투맨1",
                    "Fashion",
                    arrayListOf(
                        "http://takeastreet.com/web/product/big/201810/d6e010bb1e5c4b6981a7243d98f8e3c9.jpg",
                        "http://mitoshop.co.kr/web/product/medium/201802/13135_shop1_694780.jpg"
                    ),
                    "Test Description1",
                    15000,
                    13900,
                    "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                    "1"
                )
            )
        )
        val thumbnail = vod.vodThumbnailUrl
        val vodUrl = vod.vodUrl


        rootView.apply {
            // 썸네일
            if (vodUrl.isNotEmpty()) {
                Picasso.get().load(thumbnail).into(shopDetail_video)
            } else {
                vodVideoLayout.visibility = View.GONE
            }


            // 상품정보
            Picasso.get().load(imgDetail).into(ImgStoreDetail)


            // VOD 재생 버튼
            btn_play.setOnClickListener {
                val intent = Intent(requireContext(), VODWatchActivity::class.java)
                intent.putExtra("vod", vod)
                requireActivity().startActivity(intent)
            }

        }

        return rootView
    }


}
