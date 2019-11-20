package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.UserCartAdapter
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.fragment_user_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class UserCartFragment : Fragment() {

    lateinit var cartList: ArrayList<ItemProduct>
    private val job = Job()
    private var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_cart, container, false)

        cartList = arrayListOf()
        // Http 통신 데이터 받기
        val retrofit = Retrofit2Instance.getInstance()!!
            CoroutineScope(Dispatchers.Default + job).launch {
                try {
                    retrofit.getCartFromUserId(MyApplication.userId).execute().body().let { cart ->

                        // product01
                        val product01 = cart!!.product01
                        if (product01 != null) {
                            val temp = product01.imageUrl.split("**")
                            val productImgList = arrayListOf<String>()
                            productImgList.addAll(temp)

                            retrofit.getVideosFromProductId(product01.id).execute().body()?.forEach { video ->

                                cartList.add(
                                    ItemProduct(
                                        product01.id, product01.name, product01.categoryId, productImgList,
                                        product01.description, product01.price, product01.changedPrice,
                                        product01.detailImageUrl, video.id
                                    )
                                )
                            }


                        }

                        val product02 = cart.product02
                        if (product02 != null) {
                            val productImgList = product02.imageUrl.split("**") as ArrayList<String>

                            retrofit.getVideosFromProductId(product02.id).execute().body()?.forEach { video ->

                                cartList.add(
                                    ItemProduct(
                                        product02.id, product02.name, product02.categoryId, productImgList,
                                        product02.description, product02.price, product02.changedPrice,
                                        product02.detailImageUrl, video.id
                                    )
                                )
                            }


                        }
                    }
                }catch (e: IOException) {
                    e.printStackTrace()
                }

                for (cart in cartList) {
                    totalPrice += when (cart.productChangedPrice) {
                        -1 -> cart.productPrice
                        else -> cart.productChangedPrice
                    }
                }

                // UI
                CoroutineScope(Dispatchers.Main + job).launch {
                    rootView.apply {
                        rv_cart.adapter = UserCartAdapter(requireActivity(), cartList)
                        textTotalPrice.text = String.format("%,d원", totalPrice)
                    }
                }

            }


        rootView.apply {
            rv_cart.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                focusable = View.NOT_FOCUSABLE
            }

            btn_totalBuy.setOnClickListener {
                Toast.makeText(context, "전체 구매하러 가기", Toast.LENGTH_SHORT).show()
            }

        }


        return rootView
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
