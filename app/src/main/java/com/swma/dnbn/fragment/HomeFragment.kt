package com.swma.dnbn.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swma.dnbn.R
import com.swma.dnbn.adapter.HomeLiveAdapter
import com.swma.dnbn.adapter.HomeScheduleAdapter
import com.swma.dnbn.adapter.HomeVODAdapter
import com.swma.dnbn.adapter.SlideAdapter
import com.swma.dnbn.item.*
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.restApi.Retrofit2Service
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.*
import java.io.IOException
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
    lateinit var retrofit: Retrofit2Service
    private var check = 0
    private var refreshCheck = 0
    private val job = Job()
    private val today = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feLive)
//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feVOD)
//        IsRTL.changeShadowInRtl(requireActivity(), rootView.feSchedule)

        retrofit = Retrofit2Instance.getInstance()!!

        rootView.apply {

            // 새로고침
            refreshLayout.setOnRefreshListener {
                getHome()
            }

            // Progress
            progressBar_home.visibility = View.VISIBLE
            nestedScrollView.visibility = View.GONE
            requireActivity().lytTab.visibility = View.GONE

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

        // 슬라이드 샘플 데이터
        slideList = arrayListOf()
        slideList.add(
            ItemSlide(
                10, "남자 맨투맨", 15000, 13900,
                "http://gdimg.gmarket.co.kr/1311919434/still/600?ver=1529563272"
            )
        )
        slideList.add(
            ItemSlide(
                10, "여 맨투맨", 18000, 15900,
                "https://thumbnail11.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/images/2018/11/29/18/3/974217b4-6f64-4fb5-9fbe-6b0acbae5890.JPG"
            )
        )

        liveList = arrayListOf()
        vodList = arrayListOf()
        scheduleList = arrayListOf()

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
        // 리스트 초기화
        liveList.clear()
        vodList.clear()
        scheduleList.clear()

        // Retrofit2 비동기 코루틴으로 처리
        CoroutineScope(Dispatchers.IO + job).launch {

            try {
                // BroadCast 통신
                retrofit.getBroadcasts(2).execute().body()?.forEach {

                    // Product 정보
                    val productList: ArrayList<ItemProduct>
                    retrofit.getProductFromId(it.productId).execute().body().let { product ->

                        // 이미지 리스트 분리
                        val productImgList = product!!.imageUrl.split("**") as ArrayList<String>

                        // 상품 리스트
                        productList = arrayListOf(
                            ItemProduct(
                                product.id, product.name, product.categoryId,
                                productImgList, product.description, product.price, product.changedPrice,
                                product.detailImageUrl, null
                            )
                        )
                    }

                    // BroadCast 리스트
                    liveList.add(
                        ItemLive(
                            it.id, it.title, it.thumbnailUrl, it.categoryId,
                            it.url, it.channelId, productList, 100
                        )
                    )
                }

            }catch (e: IOException){
                e.printStackTrace()
            }

            try {
                // Video 통신
                // 5개 받아옴
                retrofit.getVideos(2).execute().body()
                    ?.forEach {
                        val productList: ArrayList<ItemProduct>

                        // Product 정보
                        retrofit.getProductFromId(it.productId).execute().body().let { product ->

                            // 이미지 리스트로 분리
                            val productImgList = product!!.imageUrl.split("**") as ArrayList<String>

                            // 각 영상에 묶인 Product 리스트
                            productList = arrayListOf(
                                ItemProduct(
                                    product.id, product.name, product.categoryId,
                                    productImgList, product.description, product.price, product.changedPrice,
                                    product.detailImageUrl, it.id
                                )
                            )
                        }

                        vodList.add(
                            ItemVOD(
                                it.id, it.name, it.thumbnailUrl, it.categoryId, it.url, it.uploaderId,
                                productList, it.uploadAt, 100
                            )
                        )
                    }

            }catch (e: IOException){
                e.printStackTrace()
            }

            try {
                // 편성표 통신
                // 0 안붙여도 됨
                retrofit.getSchedules(
                    today.year, today.monthValue, today.dayOfMonth
                ).execute().body()?.forEach {

                    // channelId 로 채 정보 조회
                    retrofit.getChannelFromChannelId(it.channelId).execute().body().let { channel ->

                        // userId 로 유저 정보 조회
                        retrofit.getUserFromUserId(channel!!.userId).execute().body().let { user ->

                            scheduleList.add(
                                ItemSchedule(
                                    it.id, it.title, user!!.id,
                                    user.name, it.productId, it.broadcastDate, it.thumbnailUrl
                                )
                            )

                        }
                    }
                }
            }catch (e: IOException){
                e.printStackTrace()
            }

            // 첫 시작
            if (refreshCheck == 0) {
                refreshCheck = 1
                // UI
                CoroutineScope(Dispatchers.Main + job).launch {
                    displayData()

                    rootView.progressBar_home.visibility = View.GONE
                    rootView.nestedScrollView.visibility = View.VISIBLE
                    requireActivity().lytTab.visibility = View.VISIBLE
                }
            }
            // 새로고침
            else {
                CoroutineScope(Dispatchers.Main + job).launch {
                    rootView.apply {
//                        rv_live.adapter = HomeLiveAdapter(requireActivity(), liveList)
//                        rv_vod.adapter = HomeVODAdapter(requireActivity(), vodList)
//                        rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)
                        rv_live.adapter!!.notifyDataSetChanged()
                        rv_vod.adapter!!.notifyDataSetChanged()
                        rv_schedule.adapter!!.notifyDataSetChanged()
                    }
                    rootView.refreshLayout.isRefreshing = false
                }

            }


        }


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
            rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)


            // 편성표 날짜 설정
            textToday.text = today.format(DateTimeFormatter.ofPattern("MM월 dd일"))
            startTimer()
        }

    }

    // ViewPager 4.5초마다 넘기기
    private fun startTimer() {
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
        check = 1
        timer.cancel()
        super.onStop()
    }

    override fun onStart() {
        // 타이머 중복 방지
        if (check == 1) {
            startTimer()
        }
        super.onStart()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
