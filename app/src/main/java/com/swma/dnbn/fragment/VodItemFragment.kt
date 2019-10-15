package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.VodItemAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_vod_item.view.*

class VodItemFragment(private val category: String) : Fragment() {

    //  Category: "전체", "푸드", "패션", "뷰티", "반려동물", "디지털/가전", "여행"

    lateinit var vodList: ArrayList<ItemVOD>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_vod_item, container, false)

        // Http 통신
        // 데이터 받기
        vodList = ArrayList()

        when (category) {
            "전체" -> {
                vodList.add(
                    ItemVOD(
                        "1",
                        "VOD 푸드",
                        "https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200",
                        "Food",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Food",
                                arrayListOf("https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200"),
                                "Test Description",
                                13000,
                                8900,
                                "",
                                "1"
                            )
                        ), "2019-10-10", 20
                    )
                )
                vodList.add(
                    ItemVOD(
                        "1",
                        "VOD 패션",
                        "https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg",
                        "Fashion",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Fashion",
                                arrayListOf("https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg"),
                                "Test Description",
                                13000,
                                8900,
                                "",
                                "2"
                            )
                        ), "2019-10-10", 20

                    )
                )
                vodList.add(
                    ItemVOD(
                        "1", "VOD 반려동물", "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg", "Pet", "",
                        "100", arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Pet",
                                arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                                "Test Des",
                                13000,
                                8900,
                                "",
                                "3"
                            )
                        ), "2019-10-10", 20
                    )
                )
            }
            "푸드" -> {
                vodList.add(
                    ItemVOD(
                        "1",
                        "VOD 푸드",
                        "https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200",
                        "Food",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Food",
                                arrayListOf("https://post-phinf.pstatic.net/MjAxODEyMjFfMTE1/MDAxNTQ1Mzc1OTYyMTA2.44XiN6bbRHARoIMgxjWXbcJE258lTS5tInlEaS_wojkg.JBkuyi7ruzEl772YoQCwKYjlhMvuslD93T7WWUD2v4wg.JPEG/%EC%B5%9C%EB%AF%B8%EC%9E%90%EC%86%8C%EB%A8%B8%EB%A6%AC%EA%B5%AD%EB%B0%A5_%281%29_woooo__jung2_%EB%8B%98_%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8.jpg?type=w1200"),
                                "Test Description",
                                13000,
                                8900,
                                "",
                                "1"
                            )
                        ), "2019-10-10", 20
                    )
                )
            }
            "패션" -> {
                vodList.add(
                    ItemVOD(
                        "1",
                        "VOD 패션",
                        "https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg",
                        "Fashion",
                        "",
                        "100",
                        arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Fashion",
                                arrayListOf("https://m.styleman.kr/web/product/medium/201903/575f601cbd3a149040669ea7b4712049.jpg"),
                                "Test Description",
                                13000,
                                8900,
                                "",
                                "2"
                            )
                        ), "2019-10-10", 20

                    )
                )
            }
            "뷰티" -> {
            }
            "반려동물" -> {
                vodList.add(
                    ItemVOD(
                        "1", "VOD 반려동물", "https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg", "Pet", "",
                        "100", arrayListOf(
                            ItemProduct(
                                "1",
                                "상품이름1",
                                "Pet",
                                arrayListOf("https://pbs.twimg.com/media/C5WybhRVMAAfBUF.jpg"),
                                "Test Des",
                                13000,
                                8900,
                                "",
                                "3"
                            )
                        ), "2019-10-10", 20
                    )
                )
            }
            "디지털/가전" -> {
            }
            else -> {
            }

        }

        // ------------------------------

        rootView.apply {
            rv_vodItem.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(activity, 2)
                focusable = View.NOT_FOCUSABLE
            }
            if (vodList.isEmpty()) {
                textVodNoData.visibility = View.VISIBLE
            } else {
                rv_vodItem.adapter = VodItemAdapter(requireActivity(), vodList)
            }

        }




        return rootView
    }

}
