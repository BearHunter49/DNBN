package com.swma.dnbn.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.ScheduleItemAdapter
import com.swma.dnbn.item.ItemSchedule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.row_toolbar.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {

    private lateinit var scheduleList: ArrayList<ItemSchedule>
    private lateinit var rootView: View

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
//        scheduleList.clear()

//        val now = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        // Http 통신
        scheduleList.add(
            ItemSchedule(
                "1", "자연산 도토리", "100", "베어헌터1",
                "2020", "2019-10-02 19:15:00",
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

        if (scheduleList.isEmpty()){
            rootView.textScheduleNoData.visibility = View.VISIBLE
        }

        rootView.rv_scheduleList.adapter!!.notifyDataSetChanged()
    }

}
