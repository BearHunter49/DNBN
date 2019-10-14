package com.swma.dnbn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.fragment.GiftIconFragment

class GiftIconPagerAdapter(fm: FragmentManager, noUse: Int, used: Int): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("미사용 $noUse", "사용 $used")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> GiftIconFragment("noUse")
            else -> GiftIconFragment("used")
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}