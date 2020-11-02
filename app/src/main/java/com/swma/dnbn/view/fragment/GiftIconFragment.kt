package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.GiftIconAdapter
import com.swma.dnbn.model.item.ItemGiftIcon
import kotlinx.android.synthetic.main.fragment_gift_icon.view.*

class GiftIconFragment(private val giftIconList: ArrayList<ItemGiftIcon>) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_gift_icon, container, false)

        rootView.apply {
            rv_giftIcon.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = GiftIconAdapter(requireActivity(), giftIconList)
            }
        }

        return rootView
    }

}
