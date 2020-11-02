package com.swma.dnbn.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.ScheduleDummy
import com.swma.dnbn.view.adapter.ScheduleItemAdapter
import com.swma.dnbn.model.item.ItemSchedule
import com.swma.dnbn.utils.MyApplication
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.row_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {

    private val scheduleList: ArrayList<ItemSchedule> = arrayListOf()
//    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().apply {
            toolbar_title.text = "편성표"
            lytTab.visibility = View.GONE
        }

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = LocalDate.now()

        scheduleDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))

        setManagerToRecyclerView()
        loadData(date)


        // DatePicker
        scheduleDatePicker.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->

                    val month = String.format("%02d", monthOfYear + 1)
                    val day = String.format("%02d", dayOfMonth)
                    scheduleDate.text = String.format("%d년 %s월 %s일", year, month, day)
                    val localDate = LocalDate.parse("$year-$month-$day")
                    loadData(localDate)
                },
                date.year,
                date.monthValue - 1,
                date.dayOfMonth
            ).show()
        }
    }

    private fun setManagerToRecyclerView(){
        rv_scheduleList.apply {
            setHasFixedSize(true)
            focusable = View.NOT_FOCUSABLE
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    /**
     * 날짜로 서버에서 편성표 리스트 받아오기 'year-month-day'
     * @param newDate = 사용자가 선택한 날짜 데이터
     */
    private fun loadData(newDate: LocalDate) {
        scheduleList.clear()

        // Dummy Data
        scheduleList.addAll(ScheduleDummy.scheduleData)

        if (scheduleList.isEmpty()) {
            textScheduleNoData.visibility = View.VISIBLE
        } else {
            textScheduleNoData.visibility = View.GONE
        }
        rv_scheduleList.adapter = ScheduleItemAdapter(requireActivity(), scheduleList)
    }


    override fun onDestroy() {
//        job.cancel()
        super.onDestroy()
    }
}
