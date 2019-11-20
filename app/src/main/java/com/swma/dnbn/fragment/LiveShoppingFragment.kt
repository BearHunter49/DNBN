package com.swma.dnbn.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.swma.dnbn.R
import com.swma.dnbn.adapter.LiveShopItemAdapter
import com.swma.dnbn.item.ItemProduct
import kotlinx.android.synthetic.main.fragment_live_shopping.view.*
import java.lang.Exception

class LiveShoppingFragment(private val productList: ArrayList<ItemProduct>) : BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()

        val window = dialog.window
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).isHideable = false
        }


        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_shopping, container, false)


        rootView.apply {
            Log.d("myTest2", productList.toString())
            rv_liveShopping.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = LinearLayoutManager(requireContext())
                adapter = LiveShopItemAdapter(requireActivity(), productList)
            }


        }

        return rootView
    }

}
