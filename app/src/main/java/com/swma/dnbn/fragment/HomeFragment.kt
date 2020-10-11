package com.swma.dnbn.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
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
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.restApi.Retrofit2Service
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.activity_main.*
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

//        retrofit = Retrofit2Instance.getInstance()!!

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
                "https://img.khan.co.kr/news/2018/04/19/l_2018042001002453300196681.jpg"
            )
        )
        slideList.add(
            ItemSlide(
                11, "여 맨투맨", 18000, 15900,
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
//        CoroutineScope(Dispatchers.IO + job).launch {
//
//            try {
//                // BroadCast 통신
//                retrofit.getBroadcasts(3).execute().body()?.forEach {
//
//                    // Product 정보
//                    val productList: ArrayList<ItemProduct> = arrayListOf()
//                    retrofit.getProductFromId(it.productId).execute().body().let { product ->
//
//                        // 이미지 리스트 분리
//                        val temp = product!!.imageUrl.split("**")
//
//                        val productImgList = arrayListOf<String>()
//                        productImgList.addAll(temp)
//                        Log.d("myTest2", productList.toString())
//
//                        retrofit.getVideosFromProductId(product.id).execute().body()?.forEach { video ->
//
//                            // 상품 리스트
//                            productList.add(
//                                ItemProduct(
//                                    product.id, product.name, product.categoryId,
//                                    productImgList, product.description, product.price, product.changedPrice,
//                                    product.detailImageUrl, video.id
//                                )
//                            )
//
//                        }
//
//                    }
//
//                    // BroadCast 리스트
//                    liveList.add(
//                        ItemLive(
//                            it.id, it.title, it.thumbnailUrl, it.categoryId,
//                            it.url, it.channelId, productList, 100
//                        )
//                    )
//                }
//
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
            // 더미데이터
            val productList: ArrayList<ItemProduct> = arrayListOf()
            val productImgList = arrayListOf(getString(R.string.test_img), getString(R.string.test_img))
            productList.add(ItemProduct(100, "테스트1", 1, productImgList,
                "Test", 9999, 7000, getString(R.string.test_img), 100))
            productList.add(ItemProduct(100, "테스트2", 1, productImgList,
                "Test", 9999, 7000, getString(R.string.test_img), 100))
            liveList.add(ItemLive(100, "테스트Live", getString(R.string.test_img), 1, "", 100, productList, 999))


//            try {
//                // Video 통신
//                // 3개 받아옴
//                retrofit.getVideos(3).execute().body()
//                    ?.forEach {
//                        val productList: ArrayList<ItemProduct>
//
//                        // Product 정보
//                        retrofit.getProductFromId(it.productId).execute().body().let { product ->
//
//                            // 이미지 리스트로 분리
//                            val temp = product!!.imageUrl.split("**")
//                            val productImgList = arrayListOf<String>()
//                            productImgList.addAll(temp)
//
//                            // 각 영상에 묶인 Product 리스트
//                            productList = arrayListOf(
//                                ItemProduct(
//                                    product.id, product.name, product.categoryId,
//                                    productImgList, product.description, product.price, product.changedPrice,
//                                    product.detailImageUrl, it.id
//                                )
//                            )
//                        }
//
//                        vodList.add(
//                            ItemVOD(
//                                it.id, it.name, it.thumbnailUrl, it.categoryId, it.url, it.uploaderId,
//                                productList, it.uploadAt, 100
//                            )
//                        )
//                    }
//
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
        // 더미데이터
        // '2019-09-25 23:25:00' 형식
        vodList.add(ItemVOD(100, "테스트VOD", getString(R.string.test_img),
            1, getString(R.string.test_video), 100, productList, "2019-09-25T23:25:00", 999))




//            try {
//                    // 편성표 통신
//                    // 0 안붙여도 됨
//                    retrofit.getSchedules(
//                        today.year, today.monthValue, today.dayOfMonth
//                    ).execute().body()?.forEach {
//
//                        // channelId 로 채 정보 조회
//                        retrofit.getChannelFromChannelId(it.channelId).execute().body().let { channel ->
//
//                            // userId 로 유저 정보 조회
//                            retrofit.getUserFromUserId(channel!!.userId).execute().body().let { user ->
//
//                                scheduleList.add(
//                                    ItemSchedule(
//                                        it.id, it.title, user!!.id,
//                                        user.name, it.productId, it.broadcastDate, it.thumbnailUrl
//                                    )
//                                )
//
//                        }
//                    }
//                }
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
            // 더미데이터
            scheduleList.add(ItemSchedule(100, "테스트Schedule", MyApplication.userId,
            MyApplication.userName, 100, "2020-10-10T23:25:00", getString(R.string.test_img)))


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
                        rv_live.adapter = HomeLiveAdapter(requireActivity(), liveList)
                        rv_vod.adapter = HomeVODAdapter(requireActivity(), vodList)
                        rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)
//                        rv_live.adapter!!.notifyDataSetChanged()
//                        rv_vod.adapter!!.notifyDataSetChanged()
//                        rv_schedule.adapter!!.notifyDataSetChanged()
                    }
                    rootView.refreshLayout.isRefreshing = false
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
            rv_live.adapter = HomeLiveAdapter(requireActivity(), liveList)

            // VOD
            rv_vod.adapter = HomeVODAdapter(requireActivity(), vodList)

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

        timer.schedule(timerTask, 1000, 4500)

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
            check = 0
        }
        super.onStart()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    // GPS 정보 수정 결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        Log.d("myTest", "HomeFragment Result")
        if (requestCode == 25 && resultCode == RESULT_OK){
            getHome()
            Log.d("HomeFragment result", "HomeFragment activityresult 받음")
            val address = data?.getStringExtra("address")
            if (address != null) {
                Log.d("HomeFragment result", address)
            }
        }



    }


}
