package com.swma.dnbn.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemCart
import kotlinx.android.synthetic.main.row_cart_item.view.*

class UserCartAdapter(private val context: Activity, private val items: ArrayList<ItemCart>): RecyclerView.Adapter<UserCartAdapter.ItemRowHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cart_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                title.text = item.productName
                price.text = String.format("%,d원", item.productPrice)
                Picasso.get().load(item.productImg).into(img)

                if (item.productDeliveryPrice == 0){
                    delivery.text = "배송비: 무료"
                } else{
                    delivery.text = String.format("배송비: %,d원", item.productDeliveryPrice)
                }


                buyBtn.setOnClickListener {
                    Toast.makeText(context, "${item.productName} 사러가기", Toast.LENGTH_SHORT).show()
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