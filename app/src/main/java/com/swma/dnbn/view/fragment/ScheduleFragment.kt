package com.swma.dnbn.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.ScheduleItemAdapter
import com.swma.dnbn.model.item.ItemSchedule
import com.swma.dnbn.utils.MyApplication
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.row_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {

    private lateinit var scheduleList: ArrayList<ItemSchedule>
    private lateinit var rootView: View
    private val job = Job()
//    private val retrofit = Retrofit2Instance.getInstance()!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().apply {
            toolbar_title.text = "편성표"
            lytTab.visibility = View.GONE
        }

        rootView = inflater.inflate(R.layout.fragment_schedule, container, false)
        val date = LocalDate.now()

        scheduleList = ArrayList()

        rootView.apply {

            scheduleDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))

            rv_scheduleList.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(requireActivity(), 2)
                adapter = ScheduleItemAdapter(requireActivity(), scheduleList)
            }

            loadData(date)

            scheduleDatePicker.setOnClickListener {
                DatePickerDialog(
                    requireActivity(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        val month = String.format("%02d", monthOfYear + 1)
                        val day = String.format("%02d", dayOfMonth)
                        rootView.scheduleDate.text = String.format("%d년 %s월 %s일", year, month, day)
                        val localDate = LocalDate.parse("$year-$month-$day")
                        loadData(localDate)
                    },
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth
                ).show()
            }
        }


        return rootView
    }

    // 날짜로 서버에서 편성표 리스트 받아오기 'year-month-day'
    private fun loadData(date: LocalDate) {
        scheduleList.clear()

        CoroutineScope(Dispatchers.Default + job).launch {
//            retrofit.getSchedules(date.year, date.monthValue, date.dayOfMonth).execute().body()?.forEach { broadcast ->
//                retrofit.getChannelFromChannelId(broadcast.channelId).execute().body().let { channel ->
//                    retrofit.getUserFromUserId(channel!!.userId).execute().body().let { user ->
//
//                        scheduleList.add(
//                            ItemSchedule(broadcast.id, broadcast.title, user!!.id, user.name, broadcast.productId,
//                                broadcast.broadcastDate, broadcast.thumbnailUrl))
//
//                    }
//                }
//            }
            // 더미데이터
            scheduleList.add(
                ItemSchedule(100, "테스트1", MyApplication.userId, MyApplication.userName, 100,
                    "2019-10-25T23:25:00", getString(R.string.test_img)))

            // UI
            CoroutineScope(Dispatchers.Main + job).launch {
                if (scheduleList.isEmpty()){
                    rootView.textScheduleNoData.visibility = View.VISIBLE
                }
                else {
                    rootView.textScheduleNoData.visibility = View.GONE
                }
                rootView.rv_scheduleList.adapter!!.notifyDataSetChanged()
            }


        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
