package com.swma.dnbn.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.view.fragment.ShopDetailFragment
import com.swma.dnbn.view.fragment.ShopReportFragment
import com.swma.dnbn.view.fragment.ShopReviewFragment
import com.swma.dnbn.model.item.ItemProduct

class ShopPagerAdapter(fm: FragmentManager, private val product: ItemProduct): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("상세정보", "리뷰", "1:1문의")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ShopDetailFragment(product)
            1 -> ShopReviewFragment()
            else -> ShopReportFragment()
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}