package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.swma.dnbn.R
import com.swma.dnbn.adapter.VodAllPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_vod_all.view.*
import kotlinx.android.synthetic.main.row_toolbar.*

class VodAllFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_vod_all, container, false)

        requireActivity().apply {
            toolbar_title.text = "VOD"
            lytTab.visibility = View.GONE
        }

        rootView.apply {
            VodAllViewPager.adapter = VodAllPagerAdapter(requireActivity().supportFragmentManager)
            VodAllViewPager.offscreenPageLimit = 6

            VodAllTabLayout.setupWithViewPager(VodAllViewPager)
            VodAllTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    VodAllViewPager.setCurrentItem(tab!!.position, false)
                }

            })

        }

        return rootView
    }


}
