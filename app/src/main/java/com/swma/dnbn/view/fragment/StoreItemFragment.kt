package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.ProductDummy
import com.swma.dnbn.view.adapter.StoreItemAdapter
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.utils.CategoryConverter
import kotlinx.android.synthetic.main.fragment_store_item.*
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
        return inflater.inflate(R.layout.fragment_store_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeItemList = ArrayList()
        val categoryNumber = CategoryConverter.categoryMap[category]

        loadData(categoryNumber)
        setManagerToRecyclerView()
        displayData()
    }


    private fun displayData() {
        if (storeItemList.isEmpty()) {
            textStoreNoData.visibility = View.VISIBLE
        } else {
            rv_storeItem.adapter = StoreItemAdapter(requireActivity(), storeItemList)
        }
    }

    private fun loadData(categoryNumber: Int?) {
        ProductDummy.productFoodData.forEach {
            if (it.productCategory == categoryNumber){
                storeItemList.add(it)
            }
        }

        ProductDummy.productClothData.forEach {
            if (it.productCategory == categoryNumber){
                storeItemList.add(it)
            }
        }
    }

    private fun setManagerToRecyclerView(){
        rv_storeItem.apply {
            setHasFixedSize(true)
            focusable = View.NOT_FOCUSABLE
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
