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

    //  Category: "전체", "푸드", "패션", "뷰티", "반려동물", "디지털/가전", "여행"

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

        val retrofit = Retrofit2Instance.getInstance()!!
        CoroutineScope(Dispatchers.Default + job).launch {
            try {
                retrofit.getVideosFromCategoryId(categoryNumber).execute().body()?.forEach { video ->
                    val productList: ArrayList<ItemProduct>

                    // Product 정보
                    retrofit.getProductFromId(video.productId).execute().body().let { product ->

                        // 이미지 리스트로 분리
                        val productImgList = product!!.imageUrl.split("**") as ArrayList<String>

                        // 각 영상에 묶인 Product 리스트
                        productList = arrayListOf(
                            ItemProduct(
                                product.id, product.name, product.categoryId,
                                productImgList, product.description, product.price, product.changedPrice,
                                product.detailImageUrl, video.id
                            )
                        )
                    }

                    vodList.add(
                        ItemVOD(
                            video.id, video.name, video.thumbnailUrl, video.categoryId, video.url, video.uploaderId,
                            productList, video.uploadAt, 100
                        )
                    )
                }
            }catch (e: IOException){
                e.printStackTrace()
            }

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
