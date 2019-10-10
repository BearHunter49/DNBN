package com.swma.dnbn.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.swma.dnbn.R
import com.swma.dnbn.adapter.LiveShopItemAdapter
import com.swma.dnbn.item.ItemProduct
import kotlinx.android.synthetic.main.fragment_live_shopping.view.*

class LiveShoppingFragment(private val productList: ArrayList<ItemProduct>) : BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_shopping, container, false)

        rootView.apply {
            rv_liveShopping.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                adapter = LiveShopItemAdapter(requireActivity(), productList)
            }


        }

        return rootView
    }

}
