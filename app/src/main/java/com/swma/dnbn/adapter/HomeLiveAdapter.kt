package com.swma.dnbn.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemLive
import kotlinx.android.synthetic.main.home_row_video_item.view.*

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

                // Retrofit2 API ProductId 사용하기
                textPrice.text = "10,000"

                // 할인 가격 있으면
                if (true){
                textPrice.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                textChangedPrice.apply {
                    text = "8,900"
                    visibility = View.VISIBLE
                }
            }

                // 클릭 리스너
                cardView.setOnClickListener {
                    Toast.makeText(context, item.liveTitle + " Clicked!", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }


    inner class ItemRowHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.home_row_video_item,
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