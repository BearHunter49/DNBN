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
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.CategoryMap
import kotlinx.android.synthetic.main.fragment_store_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

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
        val retrofit = Retrofit2Instance.getInstance()!!

        val categoryNumber = CategoryMap.categoryMap[category]!!
        CoroutineScope(Dispatchers.Default + job).launch {

            try {
                retrofit.getProductsFromCategoryId(categoryNumber).execute().body()?.forEach { product ->

                    val productImgList = product.imageUrl.split("**") as ArrayList<String>

                    retrofit.getVideosFromProductId(product.id).execute().body()!![0].let { video ->

                        storeItemList.add(
                            ItemProduct(
                                product.id, product.name, product.categoryId, productImgList, product.description,
                                product.price, product.changedPrice, product.detailImageUrl, video.id
                            )
                        )
                    }

                }
            }catch (e: IOException){
                e.printStackTrace()
            }

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