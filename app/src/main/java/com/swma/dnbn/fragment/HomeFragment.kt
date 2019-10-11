package com.swma.dnbn.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.swma.dnbn.R
import com.swma.dnbn.adapter.HomeLiveAdapter
import com.swma.dnbn.adapter.HomeScheduleAdapter
import com.swma.dnbn.adapter.HomeVODAdapter
import com.swma.dnbn.adapter.SlideAdapter
import com.swma.dnbn.item.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.row_toolbar.*
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

            // 클릭 리스너
            textLiveViewAll.setOnClickListener {
                changeFragment(LiveAllFragment(), "LIVE")
            }
            textVODViewAll.setOnClickListener {
                changeFragment(VodAllFragment(), "VOD")
            }
            textScheduleViewAll.setOnClickListener {
                changeFragment(ScheduleFragment(), "편성표")
            }
        }



        getHome()

        return rootView
    }

    private fun changeFragment(fragment: Fragment, name: String) {
        val fm = fragmentManager
        fm!!.beginTransaction().apply {
            hide(this@HomeFragment)
            add(R.id.Container, fragment, name)
            addToBackStack(name)
            commit()
        }


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
                "히어로 다람쥐",
                10000,
                7900,
                "http://optimal.inven.co.kr/upload/2017/09/23/bbs/i14062904077.jpg"
            )
        )
        slideList.add(
            ItemSlide(
                "2",
                "번개 다람쥐",
                10000,
                7900,
                "https://file.namu.moe/file/14227fd88b4493f2746fe3f47d06acb6ccbbe13b600cfded0ecee1b08e2765fcfa1ecb1be45d7b936c956e69167be31c29151de75de83b69233b79620e0bfa22c2ed9f2ce8c0b2df527a66cae2bbd99e"
            )
        )
        slideList.add(
            ItemSlide(
                "3",
                "도둑 다람쥐",
                10000,
                7900,
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg"
            )
        )

        liveList.add(
            ItemLive(
                "1",
                "소생 현장",
                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                "Food",
                "https://sywblelzxjjjqz.data.mediastore.ap-northeast-2.amazonaws.com/Test/main.m3u8",
                "100",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Pet",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                ),
                59
            )
        )
        liveList.add(
            ItemLive(
                "1",
                "방화 현장",
                "https://image.fmkorea.com/files/attach/new/20180610/33854530/1037635837/1098323360/513829981763937d94c0acd8c0f28aea.jpg",
                "Food",
                "",
                "100",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Fashion",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                ), 100
            )
        )
        liveList.add(
            ItemLive(
                "1",
                "절도 현장",
                "http://potsu.net/files/attach/images/Array/268/310/007/ad07f1d19a04d5731f3e9822112b1032.jpg",
                "Food",
                "",
                "100",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Cate1",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                ), 100
            )
        )

        vodList.add(
            ItemVOD(
                "1",
                "녹화 영상",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg",
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "베어헌터",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Cate1",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                )
            )
        )
        vodList.add(
            ItemVOD(
                "1",
                "녹화 영상",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg",
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "베어헌터",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Cate1",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                )
            )
        )
        vodList.add(
            ItemVOD(
                "1",
                "녹화 영상",
                "http://optimal.inven.co.kr/upload/2015/02/24/bbs/i1617629232.jpg",
                "Food",
                "http://cdnapi.kaltura.com/p/1878761/sp/187876100/playManifest/entryId/1_usagz19w/flavorIds/1_5spqkazq,1_nslowvhp,1_boih5aji,1_qahc37ag/format/applehttp/protocol/http/a.m3u8",
                "베어헌터",
                arrayListOf(
                    ItemProduct(
                        "1",
                        "TestName",
                        "Cate1",
                        arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                        "Test Description",
                        12000,
                        11000,
                        "http://ai.esmplus.com/chungsu1204/%EB%8F%84%ED%86%A0%EB%A6%AC%EB%AC%B5%EA%B0%80%EB%A3%A8_%EC%83%81%EC%84%B8%ED%8E%98%EC%9D%B4%EC%A7%80.jpg",
                        "1"
                    )
                )
            )
        )

        scheduleList.add(
            ItemSchedule(
                "1", "자연산 도토리", "100", "베어헌터1", "2020", "2019-10-02 19:15:00",
                "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BBNBQK1.img?h=338&w=530&m=6&q=60&o=f&l=f"
            )
        )
        scheduleList.add(
            ItemSchedule(
                "2", "비싼 도토리", "100", "베어헌터2", "2020", "2019-10-02 20:00:00",
                "https://t1.daumcdn.net/cfile/tistory/993F40405A6E911428"
            )
        )
        scheduleList.add(
            ItemSchedule(
                "3", "잣같은 잣", "100", "베어헌터3", "2020", "2019-10-02 21:00:00",
                "http://cfile223.uf.daum.net/image/223870385583895A32CC51"
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
            viewPager.currentItem = slideList.size - 1
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

    // ViewPager 4.5초마다 넘기기
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

        timer.schedule(timerTask, 0, 4500)

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
