package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.swma.dnbn.R
import com.swma.dnbn.adapter.LiveAllPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_live_all.view.*
import kotlinx.android.synthetic.main.row_toolbar.*

class LiveAllFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_all, container, false)

        requireActivity().apply {
            toolbar_title.text = "LIVE"
            lytTab.visibility = View.GONE
        }


        rootView.apply {
            liveAllViewPager.adapter = LiveAllPagerAdapter(requireActivity().supportFragmentManager)
            liveAllViewPager.offscreenPageLimit = 6

            liveAllTabLayout.setupWithViewPager(liveAllViewPager)
            liveAllTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    liveAllViewPager.setCurrentItem(tab!!.position, false)
                }

            })




        }



        return rootView
    }


}
