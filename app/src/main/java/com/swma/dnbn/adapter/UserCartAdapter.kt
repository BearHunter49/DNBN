package com.swma.dnbn.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.ShopActivity
import com.swma.dnbn.item.ItemCart
import com.swma.dnbn.item.ItemProduct
import kotlinx.android.synthetic.main.row_cart_item.view.*

class UserCartAdapter(private val context: Activity, private val items: ArrayList<ItemProduct>): RecyclerView.Adapter<UserCartAdapter.ItemRowHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cart_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                title.text = item.productName

                when(item.productChangedPrice){
                    -1 -> { price.text = String.format("%,d원", item.productPrice) }
                    else -> { price.text = String.format("%,d원", item.productChangedPrice) }
                }

                Picasso.get().load(item.productImgList[0]).into(img)
                delivery.text = "배송비: 무료"

                buyBtn.setOnClickListener {
                    val intent = Intent(context, ShopActivity::class.java)
                    intent.putExtra("product", item)
                    context.startActivity(intent)
                }
            }
        }

    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val img = view.imgCart
        val title = view.cartTitle
        val price = view.cartPrice
        val delivery = view.cartDeliveryPrice
        val buyBtn = view.cartBuy
    }

}