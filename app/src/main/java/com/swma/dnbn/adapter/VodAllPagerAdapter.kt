package com.swma.dnbn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.fragment.VodItemFragment

class VodAllPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("푸드", "패션", "디지털/가전", "생활서비스", "미용", "스포츠오락", "교육")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> VodItemFragment(titleList[position])
            1 -> VodItemFragment(titleList[position])
            2 -> VodItemFragment(titleList[position])
            3 -> VodItemFragment(titleList[position])
            4 -> VodItemFragment(titleList[position])
            5 -> VodItemFragment(titleList[position])
            else -> VodItemFragment(titleList[position])
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}