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
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.home_row_vod_item.view.*

class HomeVODAdapter(private val context: Context, private val items:ArrayList<ItemVOD>): RecyclerView.Adapter<HomeVODAdapter.ItemRowHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder{
        // different way
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_row_vod_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                textTitle.text = item.vodTitle
                Picasso.get().load(item.vodThumbnailUrl).into(image)

                textPrice.text = String.format("%,d", item.vodProduct[0].productPrice)

                // 할인 가격 있으면
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

                cardView.setOnClickListener {
                    Toast.makeText(context, item.vodTitle + " Clicked!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val textTitle = view.textProductTitle
        val image = view.image
        val textPrice = view.textProductPrice
        val textChangedPrice = view.textProductChangedPrice
        val cardView = view.videoCardView
    }

}