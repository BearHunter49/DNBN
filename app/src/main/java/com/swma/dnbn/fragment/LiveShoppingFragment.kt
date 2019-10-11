package com.swma.dnbn.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
//        val rootView = View.inflate(context, R.layout.fragment_live_shopping, null)
//        bottomSheetDialog.setContentView(rootView)
//
//        val parentView = rootView.parent as View
//        val layoutParams = parentView.layoutParams as CoordinatorLayout.LayoutParams
//        val behavior = layoutParams.behavior
//        if (behavior != null && behavior is BottomSheetBehavior){
//            behavior.bottomSheetCallback = (object: BottomSheetBehavior.BottomSheetCallback(){
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                }
//
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
////                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
////                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
////                    }
//                }
//
//            })
//            behavior.peekHeight = 300
//        }
//
//
//        rootView.apply {
//            rv_liveShopping.apply {
//                setHasFixedSize(true)
//                focusable = View.NOT_FOCUSABLE
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = LiveShopItemAdapter(requireActivity(), requireActivity().supportFragmentManager, productList)
//            }
//
//            Log.d("myTest: LiveShoppingFragment", productList.toString())
//
//        }
//
//
////        try {
////            val mBehaviorField = bottomSheetDialog.javaClass.getDeclaredField("behavior")
////            mBehaviorField.isAccessible = true
////            val behavior = mBehaviorField.get(bottomSheetDialog) as BottomSheetBehavior<*>
////            behavior.bottomSheetCallback = (object: BottomSheetBehavior.BottomSheetCallback(){
////                override fun onSlide(bottomSheet: View, slideOffset: Float) {
////                }
////
////                override fun onStateChanged(bottomSheet: View, newState: Int) {
////                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
////                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
////                    }
////                }
////
////            })
////        } catch (e: Exception){
////            e.printStackTrace()
////        }
//
//
//        return bottomSheetDialog
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_shopping, container, false)


        rootView.apply {
            rv_liveShopping.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = LinearLayoutManager(requireContext())
                adapter = LiveShopItemAdapter(requireActivity(), productList)
            }

            Log.d("myTest: LiveShoppingFragment", productList.toString())

        }

        return rootView
    }

}
