package com.swma.dnbn.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.StorePagerAdapter
import kotlinx.android.synthetic.main.fragment_store.view.*


class StoreFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store, container, false)


        rootView.apply {
            storeViewPager.adapter = StorePagerAdapter(requireFragmentManager())
            storeViewPager.offscreenPageLimit = 6
            storeTabLayout.setupWithViewPager(storeViewPager)
            storeTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    storeViewPager.setCurrentItem(tab!!.position, false)
                }

            })


        }


        return rootView
    }

}
