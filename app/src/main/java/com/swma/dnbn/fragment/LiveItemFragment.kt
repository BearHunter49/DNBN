package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.LiveItemAdapter
import com.swma.dnbn.item.ItemLive
import com.swma.dnbn.item.ItemProduct
import kotlinx.android.synthetic.main.fragment_live_item.view.*

class LiveItemFragment(private val category: String) : Fragment() {

//  Category: "전체", "푸드", "패션", "뷰티", "반려동물", "디지털/가전", "여행"

    lateinit var liveList: ArrayList<ItemLive>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_item, container, false)

        // Http 통신
        // 데이터 받기
        liveList = ArrayList()

        when (category) {
            "전체" -> {
                liveList.add(
                    ItemLive(
                        "1",
                        "푸드 테스트",
                        "https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200",
                        "Food",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
                liveList.add(
                    ItemLive(
                        "1",
                        "패션 테스트",
                        "https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg",
                        "Fashion",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
                liveList.add(
                    ItemLive(
                        "1", "반려동물 테스트", "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg", "Pet", "",
                        "100", arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
            }
            "푸드" -> {
                liveList.add(
                    ItemLive(
                        "1",
                        "푸드 테스트",
                        "https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200",
                        "Food",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
            }
            "패션" -> {
                liveList.add(
                    ItemLive(
                        "1",
                        "패션 테스트",
                        "https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg",
                        "Fashion",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
            }
            "뷰티" -> {
            }
            "반려동물" -> {
                liveList.add(
                    ItemLive(
                        "1", "반려동물 테스트", "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg", "Pet", "",
                        "100", arrayListOf(
                            ItemProduct(
                                "1",
                                "TestName",
                                "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg",
                                12000,
                                11000
                            )
                        ), 100
                    )
                )
            }
            "디지털/가전" -> {
            }
            else -> {
            }
        }
        // --------------------


        rootView.apply {

            rv_liveItem.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(activity, 2)
                focusable = View.NOT_FOCUSABLE

            }

            // 데이터 존재 유무
            if (liveList.isEmpty()) {
                textLiveNoData.visibility = View.VISIBLE
            } else {
                rv_liveItem.adapter = LiveItemAdapter(requireActivity(), liveList)
            }


        }





        return rootView
    }


}
