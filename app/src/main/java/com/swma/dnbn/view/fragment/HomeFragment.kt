package com.swma.dnbn.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.*
import com.swma.dnbn.view.adapter.HomeLiveAdapter
import com.swma.dnbn.view.adapter.HomeScheduleAdapter
import com.swma.dnbn.view.adapter.HomeVODAdapter
import com.swma.dnbn.view.adapter.SlideAdapter
import com.swma.dnbn.model.item.*
import com.swma.dnbn.utils.AppRequestCode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

const val LIVE_TAG = "LIVE"
const val VOD_TAG = "VOD"
const val SCHEDULE_TAG = "편성표"

class HomeFragment : Fragment() {
    lateinit var liveList: ArrayList<ItemLive>
    lateinit var vodList: ArrayList<ItemVOD>
    lateinit var scheduleList: ArrayList<ItemSchedule>
    lateinit var slideList: ArrayList<ItemSlide>
    lateinit var timer: Timer
    //    lateinit var rootView: View
    private var shouldTimerGo = 0
    private val job = Job()
    private val today = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 새로고침
        refreshLayout.setOnRefreshListener {
            loadData()
        }

        // Progress
        visibleProgressBar(true)

        // recyclerView
        setManagerToRecyclerView(rv_live)
        setManagerToRecyclerView(rv_vod)
        setManagerToRecyclerView(rv_schedule)

        // 클릭 리스너
        textLiveViewAll.setOnClickListener {
            changeFragment(LiveAllFragment(), LIVE_TAG)
        }
        textVODViewAll.setOnClickListener {
            changeFragment(VodAllFragment(), VOD_TAG)
        }
        textScheduleViewAll.setOnClickListener {
            changeFragment(ScheduleFragment(), SCHEDULE_TAG)
        }

        // 편성표 날짜 설정
        textToday.text = today.format(DateTimeFormatter.ofPattern("MM월 dd일"))

        loadData()
    }

    private fun setManagerToRecyclerView(rView: RecyclerView?) {
        rView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            focusable = View.NOT_FOCUSABLE
        }
    }

    private fun visibleProgressBar(valid: Boolean) {
        when (valid) {
            true -> {
                progressBar_home.visibility = View.VISIBLE
                nestedScrollView.visibility = View.GONE
                requireActivity().lytTab.visibility = View.GONE
            }
            else -> {
                progressBar_home.visibility = View.GONE
                nestedScrollView.visibility = View.VISIBLE
                requireActivity().lytTab.visibility = View.VISIBLE
            }
        }
    }

    private fun changeFragment(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            hide(this@HomeFragment)
            add(R.id.Container, fragment, name)
            addToBackStack(name)
            commit()
        }
    }

    // Home Fragment 만들기
    private fun loadData() {
        slideList.clear()
        liveList.clear()
        vodList.clear()
        scheduleList.clear()

        // 데이터 받아오기
        slideList = SlideDummy.slideData
        liveList = LiveDummy.liveData
        vodList = VodDummy.vodData
        scheduleList = ScheduleDummy.scheduleData

        // UI
        CoroutineScope(Dispatchers.Main + job).launch {
            displayData()
            visibleProgressBar(false)
            refreshLayout.isRefreshing = false
        }
    }


    // RV 어댑터 붙이기
    private fun displayData() {
        // 배너 슬라이드
        viewPager.adapter = SlideAdapter(requireActivity(), slideList)
        viewPager.offscreenPageLimit = 2
        viewPager.currentItem = slideList.size - 1
        indicator_unselected_background.setViewPager(viewPager)

        rv_live.adapter = HomeLiveAdapter(requireActivity(), liveList)
        rv_vod.adapter = HomeVODAdapter(requireActivity(), vodList)
        rv_schedule.adapter = HomeScheduleAdapter(requireActivity(), scheduleList)

        startTimer()
    }

    /**
     * ViewPager 4.5초마다 넘기는 Timer
     */
    private fun startTimer() {
        if (shouldTimerGo == 1) {
            timer = Timer()

            val timerTask = (object : TimerTask() {
                override fun run() {
                    requireActivity().runOnUiThread {

                        if (viewPager.currentItem < slideList.size - 1) {
                            viewPager.currentItem = viewPager.currentItem + 1
                        } else {
                            viewPager.currentItem = 0
                        }
                    }
                }
            })
            timer.schedule(timerTask, 1000, 4500)
            shouldTimerGo = 0
        }
    }


    override fun onStop() {
        shouldTimerGo = 1
        timer.cancel()
        super.onStop()
    }

    override fun onStart() {
        // 타이머 중복 방지
        if (shouldTimerGo == 1) {
            startTimer()
            shouldTimerGo = 0
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

        if (requestCode == AppRequestCode.LOCATION_CODE && resultCode == RESULT_OK) {
            loadData()
            Log.d("HomeFragment result", "HomeFragment activityresult 받음")

            val address = data?.getStringExtra("address")
            if (address != null) {
                Log.d("HomeFragment result", address)
            }
        }
    }
}
