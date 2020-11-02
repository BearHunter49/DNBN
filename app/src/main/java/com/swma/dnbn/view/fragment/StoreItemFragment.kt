package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.StoreItemAdapter
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.utils.CategoryMap
import kotlinx.android.synthetic.main.fragment_store_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StoreItemFragment(private val category: String) : Fragment() {

    lateinit var storeItemList: ArrayList<ItemProduct>
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store_item, container, false)

        // Http 통신
        storeItemList = ArrayList()
//        val retrofit = Retrofit2Instance.getInstance()!!

        val categoryNumber = CategoryMap.categoryMap[category]!!
        CoroutineScope(Dispatchers.Default + job).launch {

//            try {
//                retrofit.getProductsFromCategoryId(categoryNumber).execute().body()?.forEach { product ->
//
//                    val temp = product.imageUrl.split("**")
//                    val productImgList = arrayListOf<String>()
//                    productImgList.addAll(temp)
//
//                    retrofit.getVideosFromProductId(product.id).execute().body()!![0].let { video ->
//
//                        storeItemList.add(
//                            ItemProduct(
//                                product.id, product.name, product.categoryId, productImgList, product.description,
//                                product.price, product.changedPrice, product.detailImageUrl, video.id
//                            )
//                        )
//                    }
//
//                }
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
            val productImgList = arrayListOf(getString(R.string.test_img), getString(R.string.test_img))
            storeItemList.add(
                ItemProduct(
                    100, "테스트1", categoryNumber, productImgList, "Test1",
                    9999, 7000, getString(R.string.test_img), 100
                )
            )
            storeItemList.add(
                ItemProduct(
                    101, "테스트2", categoryNumber, productImgList, "Test2",
                    9999, 7000, getString(R.string.test_img), 101
                )
            )

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {
                if (storeItemList.isEmpty()) {
                    rootView.textStoreNoData.visibility = View.VISIBLE
                } else {
                    rootView.rv_storeItem.adapter = StoreItemAdapter(requireActivity(), storeItemList)
                }
            }

        }



        // -----------------------

        rootView.apply {

            rv_storeItem.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(activity, 2)
            }

        }

        return rootView
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
