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
import kotlinx.android.synthetic.main.row_live_shopping_item.view.*

class LiveShopItemAdapter(
    private val context: Activity,
    private val items: ArrayList<ItemProduct>
) : RecyclerView.Adapter<LiveShopItemAdapter.ItemRowHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_live_shopping_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                Log.d("myTest: LiveShopItemAdapter", item.toString())
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

                // 장바구니 버튼
                btn_cart.setOnClickListener {
                    Toast.makeText(context, "장바구니 추가", Toast.LENGTH_SHORT).show()
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