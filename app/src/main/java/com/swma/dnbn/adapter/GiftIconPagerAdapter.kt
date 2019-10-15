package com.swma.dnbn.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.fragment.GiftIconFragment
import com.swma.dnbn.item.ItemGiftIcon

class GiftIconPagerAdapter(fm: FragmentManager, private val noUseList: ArrayList<ItemGiftIcon>, private val usedList: ArrayList<ItemGiftIcon>): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("미사용 ${noUseList.size}", "사용 ${usedList.size}")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> GiftIconFragment(noUseList)
            else -> GiftIconFragment(usedList)
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }


}