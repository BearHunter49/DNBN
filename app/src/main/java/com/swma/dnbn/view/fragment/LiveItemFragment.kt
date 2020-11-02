package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.LiveDummy
import com.swma.dnbn.model.dummyData.ProductDummy
import com.swma.dnbn.view.adapter.LiveItemAdapter
import com.swma.dnbn.model.item.ItemLive
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.utils.CategoryConverter
import kotlinx.android.synthetic.main.fragment_live_item.*
import kotlinx.android.synthetic.main.fragment_live_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LiveItemFragment(private val category: String) : Fragment() {

    lateinit var liveList: ArrayList<ItemLive>
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_live_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 더미데이터
        val categoryNumber = CategoryConverter.categoryMap[category]!!
        liveList = arrayListOf()
        LiveDummy.liveData.forEach {
            if (it.liveCategory == categoryNumber){
                liveList.add(it)
            }
        }


        setManagerToRecyclerView()
        bindLiveData()
    }

    /**
     * Bind Live Data
     */
    private fun bindLiveData() {
        if (liveList.isEmpty()) {
            textLiveNoData.visibility = View.VISIBLE
        } else {
            rv_liveItem.adapter = LiveItemAdapter(requireActivity(), liveList)
        }
    }

    private fun setManagerToRecyclerView(){
        rv_liveItem.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            focusable = View.NOT_FOCUSABLE
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
