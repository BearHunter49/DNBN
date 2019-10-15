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
import kotlinx.android.synthetic.main.fragment_channel_product.view.*

class ChannelProductFragment(private val itemList: ArrayList<ItemProduct>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_channel_product, container, false)

        rootView.apply {
            rv_channelProduct.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(activity, 2)
                adapter = StoreItemAdapter(requireActivity(), itemList)
            }

        }



        return rootView
    }

}
