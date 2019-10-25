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

    lateinit var storeItemList: ArrayList<ItemProduct>

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
                    ItemProduct(
                        1,
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
                        1
                    )
                )
                storeItemList.add(
                    ItemProduct(
                        1,
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
                        1
                    )
                )


            }
            "푸드" -> {
            }
            "패션" -> {
                storeItemList.add(
                    ItemProduct(
                        1,
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
                        1
                    )
                )
                storeItemList.add(
                    ItemProduct(
                        1,
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
                        1
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
