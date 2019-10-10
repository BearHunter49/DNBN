package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.StoreItemAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_store_item.view.*

class StoreItemFragment(private val category: String) : Fragment() {

    lateinit var storeItemList: ArrayList<ItemVOD>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store_item, container, false)

        // Http 통신
        //
        storeItemList = ArrayList()
        when (category) {
            "전체" -> {
                storeItemList.add(
                    ItemVOD(
                        "1",
                        "신상 맨투맨",
                        "http://img.gqkorea.co.kr/gq/2017/08/style_59896c152a6a7.jpg",
                        "Fashion",
                        "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8",
                        "베어헌터",
                        "1010",
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        arrayListOf(
                            ItemProduct(
                                "1", "상품이름1", "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg",
                                13000, 8900
                            ), ItemProduct(
                                "1", "상품이름2", "http://mitoshop.co.kr/web/product/medium/201802/13135_shop1_694780.jpg",
                                15000, 10900
                            )
                        )
                    )
                )

            }
            "푸드" -> {
            }
            "패션" -> {
                storeItemList.add(
                    ItemVOD(
                        "1",
                        "신상 맨투맨",
                        "http://img.gqkorea.co.kr/gq/2017/08/style_59896c152a6a7.jpg",
                        "Fashion",
                        "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8",
                        "베어헌터",
                        "1010",
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        arrayListOf(
                            ItemProduct(
                                "1", "상품이름1", "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg",
                                13000, 8900
                            ), ItemProduct(
                                "1", "상품이름2", "http://mitoshop.co.kr/web/product/medium/201802/13135_shop1_694780.jpg",
                                15000, 10900
                            )
                        )
                    )
                )
            }
            "뷰티" -> {
            }
            "반려동물" -> {
            }
            "디지털/가전" -> {
            }
            else -> {
            }
        }

        // -----------

        rootView.apply {

            rv_storeItem.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(activity, 2)
            }


            if (storeItemList.isEmpty()) {
                textStoreNoData.visibility = View.VISIBLE
            } else {
                rv_storeItem.adapter = StoreItemAdapter(requireActivity(), storeItemList)
            }


        }

        return rootView
    }

}
