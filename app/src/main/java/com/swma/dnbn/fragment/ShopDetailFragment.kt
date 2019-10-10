package com.swma.dnbn.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import com.swma.dnbn.R
import com.swma.dnbn.VODWatchActivity
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_shop_detail.view.*

class ShopDetailFragment(private val product: ItemVOD) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shop_detail, container, false)

        //
        // 상품 정보
        val thumbnail = product.vodImageUrl
        val vodUrl = product.vodUrl
        val imgDetail = product.vodDetailImg

        rootView.apply {
            // 썸네일
            if (vodUrl.isNotEmpty()){
                Picasso.get().load(thumbnail).into(shopDetail_video)
            }
            else{
                vodVideoLayout.visibility = View.GONE
            }


            // 상품정보
            Picasso.get().load(imgDetail).into(ImgStoreDetail)



            // VOD 재생 버튼
            btn_play.setOnClickListener {
                val intent = Intent(requireContext(), VODWatchActivity::class.java)
                intent.putExtra("product", product)
                requireActivity().startActivity(intent)
            }

        }

        return rootView
    }


}
