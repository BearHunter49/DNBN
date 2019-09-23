package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemLive
import com.swma.dnbn.item.ItemSchedule
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.util.IsRTL
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    lateinit var liveList: ArrayList<ItemLive>
    lateinit var vodList: ArrayList<ItemVOD>
    lateinit var scheduleList: ArrayList<ItemSchedule>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        IsRTL.changeShadowInRtl(requireActivity(), feLive)
        IsRTL.changeShadowInRtl(requireActivity(), feVOD)
        IsRTL.changeShadowInRtl(requireActivity(), feSchedule)

        // recyclerView
        rv_live.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            focusable = View.NOT_FOCUSABLE
        }

        rv_vod.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            focusable = View.NOT_FOCUSABLE
        }

        rv_schedule.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            focusable = View.NOT_FOCUSABLE
        }

        getHome()


        return rootView
    }

    // Home Fragment 만들기
    private fun getHome() {
        // Retrofit2 로 HTTP 통신 하기
        //
        //
        liveList.add(ItemLive("1", "TestTitle", getString(R.string.testimgurl), "Food", getString(R.string.testplayurl),
            "bearhunter", "1010"))

        vodList.add(ItemVOD("1", "TestTitle", getString(R.string.testimgurl), "Food", "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
            "foodhunter", "2020", "TestTestTest"))

        scheduleList.add(ItemSchedule("1", "bearhunter", "2020", "20190923"))

        displayData()

    }

    private fun displayData() {

    }


}
