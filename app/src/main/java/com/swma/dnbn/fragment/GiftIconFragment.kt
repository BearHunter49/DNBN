package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.GiftIconAdapter
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.fragment_gift_icon.view.*

class GiftIconFragment(private val isUse: String) : Fragment() {

    private lateinit var gifticonList: ArrayList<ItemGiftIcon>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_gift_icon, container, false)

        // Http 통신
        // 상황에 맞는 기프티콘 리스트

        gifticonList = arrayListOf()
        when (isUse) {
            "noUse" -> {
                gifticonList.add(
                    ItemGiftIcon(
                        "츀힌",
                        "BBQ",
                        "https://img.bbq.co.kr:449/uploads/bbq_d/thumbnail/gold_olive_list_0423.jpg",
                        "https://www.cognex.com/BarcodeGenerator/Content/images/isbn.png",
                        "1234567",
                        "2019-10-14",
                        0
                    )

                )

            }
            "used" -> {
                gifticonList.add(
                    ItemGiftIcon(
                        "핏자",
                        "도민호",
                        "https://cdn.dominos.co.kr/admin/upload/goods/20180827_ZIna5r8b.jpg",
                        "https://www.cognex.com/BarcodeGenerator/Content/images/isbn.png",
                        "9876543",
                        "2019-10-12",
                        1
                    )

                )
            }
        }


        rootView.apply {

            rv_giftIcon.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = GiftIconAdapter(requireActivity(), gifticonList)
            }


        }


        return rootView
    }

}
