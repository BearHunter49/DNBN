package com.swma.dnbn.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.VodItemAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.CategoryMap
import kotlinx.android.synthetic.main.fragment_vod_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class VodItemFragment(private val category: String) : Fragment() {

    lateinit var vodList: ArrayList<ItemVOD>
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_vod_item, container, false)

        // Http 통신
        // 데이터 받기
        vodList = arrayListOf()
        val categoryNumber = CategoryMap.categoryMap[category]!!

//        val retrofit = Retrofit2Instance.getInstance()!!
        CoroutineScope(Dispatchers.Default + job).launch {
//            try {
//                retrofit.getVideosFromCategoryId(categoryNumber).execute().body()?.forEach { video ->
//                    val productList: ArrayList<ItemProduct>
//
//                    // Product 정보
//                    retrofit.getProductFromId(video.productId).execute().body().let { product ->
//
//                        // 이미지 리스트로 분리
//                        val temp = product!!.imageUrl.split("**")
//                        val productImgList = arrayListOf<String>()
//                        productImgList.addAll(temp)
//
//
//                        // 각 영상에 묶인 Product 리스트
//                        productList = arrayListOf(
//                            ItemProduct(
//                                product.id, product.name, product.categoryId,
//                                productImgList, product.description, product.price, product.changedPrice,
//                                product.detailImageUrl, video.id
//                            )
//                        )
//                    }
//
//                    vodList.add(
//                        ItemVOD(
//                            video.id, video.name, video.thumbnailUrl, video.categoryId, video.url, video.uploaderId,
//                            productList, video.uploadAt, 100
//                        )
//                    )
//                }
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
            val productList = arrayListOf(
                            ItemProduct(
                                100, "테스트1", 1,
                                arrayListOf(getString(R.string.test_img)), "Test1", 9999, 7000,
                                getString(R.string.test_img), 100
                            )
                        )
            vodList.add(
                ItemVOD(
                    100, "테스트1", getString(R.string.test_img), 1, getString(R.string.test_video), 100,
                    productList, "2019-09-25T23:25:00", 999
                )
            )

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {
                if (vodList.isEmpty()) {
                    rootView.textVodNoData.visibility = View.VISIBLE
                } else {
                    rootView.rv_vodItem.adapter = VodItemAdapter(requireActivity(), vodList)
                }
            }

        }

        // ------------------------------

        rootView.apply {
            rv_vodItem.apply {
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
