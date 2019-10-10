package com.swma.dnbn.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.row_vod_item.view.*

class VodItemAdapter(private val context: Activity, private val items: ArrayList<ItemVOD>) :
    RecyclerView.Adapter<VodItemAdapter.ItemRowHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_vod_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                textTitle.text = item.vodTitle
                Picasso.get().load(item.vodImageUrl).into(image)

                textPrice.text = String.format("%,d", item.vodProduct[0].productPrice)

                // 할인 가격 처리
                if (item.vodProduct[0].productChangedPrice != -1){
                    textPrice.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                    }
                    textChangedPrice.apply {
                        text = String.format("%,d", item.vodProduct[0].productChangedPrice)
                        visibility = View.VISIBLE
                    }
                }

                // 클릭 리스너
                cardView.setOnClickListener {
                    Toast.makeText(context, item.vodTitle + " Clicked!", Toast.LENGTH_SHORT).show()
                }


            }
        }

    }


    inner class ItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle = itemView.textProductTitle
        val image = itemView.image
        val textPrice = itemView.textProductPrice
        val textChangedPrice = itemView.textProductChangedPrice
        val cardView = itemView.videoCardView
    }


}