package com.swma.dnbn.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.swma.dnbn.R
import com.swma.dnbn.adapter.HomeLiveAdapter
import com.swma.dnbn.adapter.HomeScheduleAdapter
import com.swma.dnbn.adapter.HomeVODAdapter
import com.swma.dnbn.adapter.SlideAdapter
import com.swma.dnbn.item.ItemLive
import com.swma.dnbn.item.ItemSchedule
import com.swma.dnbn.item.ItemSlide
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var liveList: ArrayList<ItemLive>
    lateinit var vodList: ArrayList<ItemVOD>
    lateinit var scheduleList: ArrayList<ItemSchedule>
    lateinit var slideList: ArrayList<ItemSlide>
    lateinit var rootView: View
    lateinit var timer: Timer
    private var check = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feLive)
//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feVOD)
//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feSchedule)

        rootView.apply {

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
        }


        getHome()


        return rootView
    }

    // Home Fragment 만들기
    private fun getHome() {
        // Retrofit2 로 HTTP 통신 하기
        // 데이터 넣기
        //
        slideList = ArrayList()
        liveList = ArrayList()
        vodList = ArrayList()
        scheduleList = ArrayList()

        slideList.add(
            ItemSlide(
                "1",
                "올 신상품",
                "10,000",
                "7,900",
                "http://optimal.inven.co.kr/upload/2017/09/23/bbs/i14062904077.jpg"
            )
        )
        slideList.add(
            ItemSlide(
                "1",
                "올 신상품",
                "10,000",
                "7,900",
                "http://optimal.inven.co.kr/upload/2017/09/23/bbs/i14062904077.jpg"
            )
        )
        slideList.add(
            ItemSlide(
                "1",
                "올 신상품",
                "10,000",
                "7,900",
                "http://optimal.inven.co.kr/upload/2017/09/23/bbs/i14062904077.jpg"
            )
        )

        liveList.add(
            ItemLive(
                "1", "작년 신상품", getString(R.string.testimgurl), "Food", getString(R.string.testplayurl),
                "100", "1010"
            )
        )
        liveList.add(
            ItemLive(
                "1", "작년 신상품", getString(R.string.testimgurl), "Food", getString(R.string.testplayurl),
                "100", "1010"
            )
        )
        liveList.add(
            ItemLive(
                "1", "작년 신상품", getString(R.string.testimgurl), "Food", getString(R.string.testplayurl),
                "100", "1010"
            )
        )

        vodList.add(
            ItemVOD(
                "1",
                "내일 신상품",
                getString(R.string.testimgurl),
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "120",
                "2020",
                "TestTestTest"
            )
        )
        vodList.add(
            ItemVOD(
                "1",
                "내일 신상품",
                getString(R.string.testimgurl),
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "120",
                "2020",
                "TestTestTest"
            )
        )
        vodList.add(
            ItemVOD(
                "1",
                "내일 신상품",
                getString(R.string.testimgurl),
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "120",
                "2020",
                "TestTestTest"
            )
        )

        scheduleList.add(
            ItemSchedule(
                "1", "내년 신상품", "100", "bearhunter", "2020", "오늘 저녁 7:15",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg"
            )
        )
        scheduleList.add(
            ItemSchedule(
                "1", "내년 신상품", "100", "bearhunter", "2020", "오늘 저녁 7:15",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg"
            )
        )
        scheduleList.add(
            ItemSchedule(
                "1", "내년 신상품", "100", "bearhunter", "2020", "오늘 저녁 7:15",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg"
            )
        )


        displayData()
    }

    // RV 어댑터 붙이기
    private fun displayData() {

        rootView.apply {

            // 배너 슬라이드
            viewPager.adapter = SlideAdapter(requireActivity(), slideList)
            viewPager.offscreenPageLimit = 2
            indicator_unselected_background.setViewPager(viewPager)

            // Live
            if (liveList.isEmpty()) {
                lytHomeLive.visibility = View.GONE
            } else {
                rv_live.adapter = HomeLiveAdapter(requireActivity(), liveList)
            }

            // VOD
            if (vodList.isEmpty()) {
                lytHomeVOD.visibility = View.GONE
            } else {
                rv_vod.adapter = HomeVODAdapter(requireActivity(), vodList)
            }

            // 편성표
            if (scheduleList.isEmpty()) {
                lytHomeSchedule.visibility = View.GONE
            } else {
                rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)
            }


            // 편성표 날짜 설정
            textToday.text = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"))
            startTimer()

        }

    }

    // ViewPager 6초마다 넘기기
    private fun startTimer() {
        Log.d("myTest", "startTimer()")
        timer = Timer()

        val timerTask = (object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    rootView.apply {
                        if (viewPager.currentItem < slideList.size - 1) {
                            viewPager.currentItem = viewPager.currentItem + 1
                        } else {
                            viewPager.currentItem = 0
                        }
                    }
                }
            }
        })

        timer.schedule(timerTask, 0, 6000)

    }


    override fun onStop() {
        Log.d("myTest", "onStop()")
        timer.cancel()
        check = 1
        super.onStop()
    }

    override fun onStart() {
        Log.d("myTest", "onStart()")
        if (check != 0) {
            startTimer()
        }
        super.onStart()
    }


}
