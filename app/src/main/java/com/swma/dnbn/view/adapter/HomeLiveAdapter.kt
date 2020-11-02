package com.swma.dnbn.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.view.activity.LiveWatchActivity
import com.swma.dnbn.R
import com.swma.dnbn.model.item.ItemLive
import kotlinx.android.synthetic.main.home_row_live_item.view.*

class HomeLiveAdapter(private val context: Context, private val items: ArrayList<ItemLive>) :
    RecyclerView.Adapter<HomeLiveAdapter.ItemRowHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemRowHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {

        // Bind Item
        items[position].let { item ->
            with(holder){
                textTitle.text = item.liveTitle
                Picasso.get().load(item.liveImageUrl).into(image)

                textPrice.text = String.format("%,d", item.liveProduct[0].productPrice)

                // 할인 가격 있으면
                if (item.liveProduct[0].productChangedPrice != -1){
                textPrice.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                textChangedPrice.apply {
                    text = String.format("%,d", item.liveProduct[0].productChangedPrice)
                    visibility = View.VISIBLE
                }
            }

                // 클릭 리스너
                cardView.setOnClickListener {
                    val intent = Intent(context, LiveWatchActivity::class.java)
                    intent.putExtra("live", item)

                    context.startActivity(intent)
                }

            }
        }

    }


    inner class ItemRowHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.home_row_live_item,
            parent,
            false
        )
    ) {
        val textTitle = itemView.textProductTitle
        val image = itemView.image
        val textPrice = itemView.textProductPrice
        val textChangedPrice = itemView.textProductChangedPrice
        val cardView = itemView.videoCardView
    }
}