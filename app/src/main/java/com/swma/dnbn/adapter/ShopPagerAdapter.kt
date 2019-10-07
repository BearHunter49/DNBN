package com.swma.dnbn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.fragment.ShopDetailFragment
import com.swma.dnbn.fragment.ShopReportFragment
import com.swma.dnbn.fragment.ShopReviewFragment

class ShopPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("상세정보", "리뷰", "1:1문의")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ShopDetailFragment()
            1 -> ShopReviewFragment()
            else -> ShopReportFragment()
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}