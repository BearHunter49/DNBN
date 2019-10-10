package com.swma.dnbn.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.ShopActivity
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.row_store_item.view.*

class StoreItemAdapter(private val context: Activity, private val items: ArrayList<ItemVOD>) :
    RecyclerView.Adapter<StoreItemAdapter.ItemRowHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_store_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                title.text = item.vodTitle
                Picasso.get().load(item.vodImageUrl).into(image)

                price.text = String.format("%,d", item.vodProduct[0].productPrice)

                // 할인 가격 처리
                if (item.vodProduct[0].productChangedPrice != -1){
                    price.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                    }
                    changedPrice.apply {
                        text = String.format("%,d", item.vodProduct[0].productChangedPrice)
                        visibility = View.VISIBLE
                    }
                }

                cardView.setOnClickListener {
                    val intent = Intent(context, ShopActivity::class.java)
                    intent.putExtra("item", item)
                    context.startActivity(intent)
                }

            }
        }

    }


    inner class ItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.textProductTitle
        val price = view.textProductPrice
        val changedPrice = view.textProductChangedPrice
        val image = view.image
        val cardView = view.storeCardView
    }


}