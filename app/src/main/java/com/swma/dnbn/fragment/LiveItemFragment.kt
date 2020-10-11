package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.LiveItemAdapter
import com.swma.dnbn.item.ItemLive
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.CategoryMap
import kotlinx.android.synthetic.main.fragment_live_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class LiveItemFragment(private val category: String) : Fragment() {

    lateinit var liveList: ArrayList<ItemLive>
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_item, container, false)



        // Http 통신
        // 데이터 받기
        liveList = arrayListOf()
        val categoryNumber = CategoryMap.categoryMap[category]!!

//        val retrofit = Retrofit2Instance.getInstance()!!
        CoroutineScope(Dispatchers.Default + job).launch {
//            try {
//                retrofit.getBroadcastsFromCategoryId(categoryNumber).execute().body()?.forEach { broadcast ->
//
//                    // Product 정보
//                    val productList: ArrayList<ItemProduct> = arrayListOf()
//                    retrofit.getProductFromId(broadcast.productId).execute().body().let { product ->
//
//                        // 이미지 리스트 분리
//                        val temp = product!!.imageUrl.split("**")
//                        val productImgList = arrayListOf<String>()
//                        productImgList.addAll(temp)
//
//                        retrofit.getVideosFromProductId(product.id).execute().body()?.forEach { video ->
//
//                            // 상품 리스트
//                            productList.add(
//                                ItemProduct(
//                                    product.id, product.name, product.categoryId,
//                                    productImgList, product.description, product.price, product.changedPrice,
//                                    product.detailImageUrl, video.id
//                                )
//                            )
//                        }
//
//
//                    }
//
//                    // BroadCast 리스트
//                    liveList.add(
//                        ItemLive(
//                            broadcast.id, broadcast.title, broadcast.thumbnailUrl, broadcast.categoryId,
//                            broadcast.url, broadcast.channelId, productList, 100
//                        )
//                    )
//                }
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
            // 더미데이터
            val productList: ArrayList<ItemProduct> = arrayListOf()
            productList.add(
                ItemProduct(
                    100, "테스트1", 1,
                    arrayListOf(getString(R.string.test_img)), "Test1", 9999, 7000,
                    getString(R.string.test_img), 100
                )
            )
            liveList.add(
                ItemLive(
                    100, "테스트Live", getString(R.string.test_img), 1,
                    "", 100, productList, 999
                )
            )

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {
                // 데이터 존재 유무
                if (liveList.isEmpty()) {
                    rootView.textLiveNoData.visibility = View.VISIBLE
                } else {
                    rootView.rv_liveItem.adapter = LiveItemAdapter(requireActivity(), liveList)
                }
            }
        }




        // --------------------


        rootView.apply {

            rv_liveItem.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(activity, 2)
                focusable = View.NOT_FOCUSABLE
            }
        }


        return rootView
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
