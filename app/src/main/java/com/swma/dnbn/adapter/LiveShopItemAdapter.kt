package com.swma.dnbn.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.ShopActivity
import com.swma.dnbn.item.ItemProduct
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.restApiData.CartData
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.row_live_shopping_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class LiveShopItemAdapter(
    private val context: Activity,
    private val items: ArrayList<ItemProduct>
) : RecyclerView.Adapter<LiveShopItemAdapter.ItemRowHolder>() {

//    private val retrofit = Retrofit2Instance.getInstance()!!
    private val job = Job()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_live_shopping_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                Picasso.get().load(item.productImgList[0]).into(img)
                productName.text = item.productName
                deliveryPrice.text = "무료배송"

                price.text = String.format("%,d", item.productPrice)
                // 할인 가격 처리
                if (item.productChangedPrice != -1) {
                    price.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTextColor(ContextCompat.getColor(context, R.color.dark_gray))
                    }
                    changedPrice.apply {
                        text = String.format("%,d", item.productChangedPrice)
                        visibility = View.VISIBLE
                    }
                }

                // 상품 클릭
                constView.setOnClickListener {
                    val intent = Intent(context, ShopActivity::class.java)
                    intent.putExtra("product", item)
                    context.startActivity(intent)
                }

                // 장바구니 버튼 -> Http 통신하기 POST
                btn_cart.setOnClickListener {
                    Toast.makeText(context, "현재 지원이 안됩니다!", Toast.LENGTH_SHORT).show()
//                    try {
//                        CoroutineScope(Dispatchers.Default + job).launch {
////                            val response = retrofit.addCart(MyApplication.userId, item.productId, 1).execute()
//                            if (response.isSuccessful){
//                                // UI
//                                CoroutineScope(Dispatchers.Main + job).launch {
//                                    Toast.makeText(context, "${item.productName}\n장바구니 추가!", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        }
//
//                    }catch (e: IOException){
//                        e.printStackTrace()
//                        Toast.makeText(context, "장바구니 추가 실패..", Toast.LENGTH_SHORT).show()
//                    }

                }


            }
        }
    }


    inner class ItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img = view.imgLiveShopping
        val productName = view.liveShopTitle
        val price = view.liveShopPrice
        val changedPrice = view.liveShopChangedPrice
        val deliveryPrice = view.liveShopDeliveryPrice
        val constView = view.liveShopView
        val btn_cart = view.btn_liveShopCart

    }

}