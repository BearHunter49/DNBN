package com.swma.dnbn.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import com.swma.dnbn.R
import com.swma.dnbn.model.dummyData.VodDummy
import com.swma.dnbn.view.activity.VODWatchActivity
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.model.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_shop_detail.*
import kotlinx.android.synthetic.main.fragment_shop_detail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ShopDetailFragment(private val product: ItemProduct) : Fragment() {

    private val job = Job()
    lateinit var vod: ItemVOD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 넘어온 상품 정보
        bindProductData(product)


        // VOD 재생 버튼
        btn_play.setOnClickListener {
            val intent = Intent(requireContext(), VODWatchActivity::class.java)
            intent.putExtra("vod", vod)
            requireActivity().startActivity(intent)
        }
    }

    private fun bindProductData(product: ItemProduct) {
        val vodId = product.productVODId
        val imgDetail = product.productDetailImg

        // 영상 정보가 있는 상품일때만
        if (vodId != null) {
            // 더미데이터
            vod = VodDummy.vodData[0]
            Picasso.get().load(vod.vodThumbnailUrl).into(shopDetail_video)
        } else {
            vodVideoLayout.visibility = View.GONE
        }

        Picasso.get().load(imgDetail).into(ImgStoreDetail)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
