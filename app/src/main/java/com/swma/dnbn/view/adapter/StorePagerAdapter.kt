package com.swma.dnbn.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.view.fragment.StoreItemFragment

class StorePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("푸드", "패션", "디지털/가전", "생활서비스", "미용", "스포츠오락", "교육")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> StoreItemFragment(titleList[position])
            1 -> StoreItemFragment(titleList[position])
            2 -> StoreItemFragment(titleList[position])
            3 -> StoreItemFragment(titleList[position])
            4 -> StoreItemFragment(titleList[position])
            5 -> StoreItemFragment(titleList[position])
            else -> StoreItemFragment(titleList[position])
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}