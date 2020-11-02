package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.ProductDummy
import com.swma.dnbn.model.dummyData.VodDummy
import com.swma.dnbn.view.adapter.VodItemAdapter
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.model.item.ItemVOD
import com.swma.dnbn.utils.CategoryConverter
import kotlinx.android.synthetic.main.fragment_vod_item.*
import kotlinx.android.synthetic.main.fragment_vod_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VodItemFragment(private val category: String) : Fragment() {

    private val vodList: ArrayList<ItemVOD> = arrayListOf()
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vod_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryNumber = CategoryConverter.categoryMap[category]!!

        getData(categoryNumber)

        if (vodList.isEmpty()) {
            textVodNoData.visibility = View.VISIBLE
        } else {
            rv_vodItem.adapter = VodItemAdapter(requireActivity(), vodList)
        }

        rv_vodItem.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            focusable = View.NOT_FOCUSABLE
        }
    }

    private fun getData(categoryNumber: Int) {
        // Dummy Data
        VodDummy.vodData.forEach {
            if (it.vodCategory == categoryNumber){
                vodList.add(it)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
