package com.swma.dnbn.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.swma.dnbn.R
import com.swma.dnbn.adapter.HomeLiveAdapter
import com.swma.dnbn.adapter.HomeScheduleAdapter
import com.swma.dnbn.adapter.HomeVODAdapter
import com.swma.dnbn.adapter.SlideAdapter
import com.swma.dnbn.item.*
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.restApiData.ProductData
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.*
import org.jetbrains.annotations.NotNull
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

        rootView.apply {

            // Progress
            progressBar_home.visibility = View.VISIBLE
            nestedScrollView.visibility = View.GONE

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

        val retrofit = Retrofit2Instance.getInstance()

        // Retrofit2 비동기 코루틴으로 처리
        CoroutineScope(Dispatchers.IO + job).launch {

            // BroadCast 통신
            val response = retrofit!!.getBroadcasts("5").execute().body()

            response?.forEach {
                val productList: ArrayList<ItemProduct>

                retrofit.getProductFromId(it.productId).execute().body().let { product ->
                    val productImgList = product!!.imageUrl.split("**") as ArrayList<String>
                    productList = arrayListOf(
                        ItemProduct(
                            product.id, product.name, product.categoryId.toString(),
                            productImgList, product.description, product.price, product.changedPrice,
                            product.detailImgUrl, it.url
                        )
                    )
                }


                liveList.add(
                    ItemLive(
                        it.id, it.title, it.thumbnailUrl, it.categoryId.toString(),
                        it.url, it.channelId, productList, 100
                    )
                )
            }

            // Video 통신
            // 5개 받아옴
            val response2 = retrofit.getVideos("5").execute().body()

            response2?.forEach {
                val productList: ArrayList<ItemProduct>

                // Product 조회
                retrofit.getProductFromId(it.productId).execute().body().let { product ->

                    // 이미지 리스트로 분리
                    val productImgList = product!!.imageUrl.split("**") as ArrayList<String>

                    // 각 영상에 묶인 상품 리스트 받기
                    productList = arrayListOf(
                        ItemProduct(
                            product.id, product.name, product.categoryId.toString(),
                            productImgList, product.description, product.price, product.changedPrice,
                            product.detailImgUrl, it.url
                        )
                    )
                }

                vodList.add(
                    ItemVOD(
                        it.id, it.name, it.thumbnailUrl, it.categoryId.toString(), it.url, it.uploaderId,
                        productList, "", 100
                    )
                )
            }


            // 편성표 통신
            // 0 붙이
            val response3 = retrofit.getSchedules(
                today.year, today.monthValue, today.dayOfMonth
            ).execute().body()

            response3?.forEach {

                // ChannelId 로 유저 정보 조회
                retrofit.getChannelFromChannelId(it.channelId).execute().body().let { channel ->

                    retrofit.getUserFromUserId(channel!!.userId).execute().body().let { user ->

                        scheduleList.add(
                            ItemSchedule(
                                it.id, it.title, channel.userId,
                                user!!.name, it.productId, it.broadcastDate.toString(), it.thumbnailUrl
                            )
                        )

                    }


                }


            }

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {

                displayData()

                rootView.progressBar_home.visibility = View.GONE
                rootView.nestedScrollView.visibility = View.VISIBLE
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
            if (scheduleList.isEmpty()) {
                lytHomeSchedule.visibility = View.GONE
            } else {
                rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)
            }


            // 편성표 날짜 설정
            textToday.text = today.format(DateTimeFormatter.ofPattern("MM월 dd일"))
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
        if (check == 1) {
            timer.cancel()
        } else {
            check = 1
        }
        super.onStop()
    }

    override fun onStart() {
        Log.d("myTest", "onStart()")

        if (check != 0) {
            startTimer()
        }
        super.onStart()
    }

    override fun onDestroy() {
        job.cancel()
        Log.d("myTest", "onDestroy")
        super.onDestroy()
    }


}
