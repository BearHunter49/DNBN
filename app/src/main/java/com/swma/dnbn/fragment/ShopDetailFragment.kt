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
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.restApi.Retrofit2Instance
import kotlinx.android.synthetic.main.fragment_shop_detail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShopDetailFragment(private val product: ItemProduct) : Fragment() {

    private val job = Job()
    lateinit var vod: ItemVOD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shop_detail, container, false)

        // 넘어온 상품 정보
        val vodId = product.productVODId
        val imgDetail = product.productDetailImg

        // Http 통신
        // vodId를 통해 VOD 정보 얻기
        val retrofit = Retrofit2Instance.getInstance()!!

        // 영상 정보가 있는 상품일때만
        if (vodId != null) {
            CoroutineScope(Dispatchers.IO + job).launch {
                retrofit.getVideoFromId(vodId).execute().body().let { video ->
                    vod = ItemVOD(
                        vodId, video!!.name, video.thumbnailUrl, video.categoryId,
                        video.url, video.uploaderId, arrayListOf(product), "", 100
                    )

                    // UI
                    CoroutineScope(Dispatchers.Main + job).launch {
                        rootView.apply {
                            Picasso.get().load(video.thumbnailUrl).into(shopDetail_video)

                        }
                    }
                }
            }
        }
        // 영상 정보가 없을 경우
        else{
            rootView.vodVideoLayout.visibility = View.GONE
        }



        rootView.apply {

            // 상품지 상세정보 이미지
            Picasso.get().load(imgDetail).into(ImgStoreDetail)

            // VOD 재생 버튼
            btn_play.setOnClickListener {
                val intent = Intent(requireContext(), VODWatchActivity::class.java)
                intent.putExtra("vod", vod)
                requireActivity().startActivity(intent)
            }

        }

        return rootView
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
