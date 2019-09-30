package com.swma.dnbn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.fragment.LiveItemFragment

class LiveAllPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("전체", "푸드", "패션", "뷰티", "반려동물", "디지털/가전", "여행")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> LiveItemFragment(titleList[position])
            1 -> LiveItemFragment(titleList[position])
            2 -> LiveItemFragment(titleList[position])
            3 -> LiveItemFragment(titleList[position])
            4 -> LiveItemFragment(titleList[position])
            5 -> LiveItemFragment(titleList[position])
            else -> LiveItemFragment(titleList[position])
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}