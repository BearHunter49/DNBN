package com.swma.dnbn.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.view.fragment.ChannelProductFragment
import com.swma.dnbn.view.fragment.ChannelVODFragment
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.model.item.ItemVOD

class ChannelPagerAdapter(
    fm: FragmentManager,
    private val vodList: ArrayList<ItemVOD>,
    private val itemList: ArrayList<ItemProduct>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList = listOf("영상 ${vodList.size}", "상품 ${itemList.size}")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ChannelVODFragment(vodList)
            else -> ChannelProductFragment(itemList)
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}