package com.swma.dnbn.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swma.dnbn.view.fragment.UserCartFragment
import com.swma.dnbn.view.fragment.UserShoppingFragment

class UserPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val titleList = listOf("나의 쇼핑", "장바구니")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> UserShoppingFragment()
            1 -> UserCartFragment()
            else -> UserShoppingFragment()
        }
    }

    override fun getCount() = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}