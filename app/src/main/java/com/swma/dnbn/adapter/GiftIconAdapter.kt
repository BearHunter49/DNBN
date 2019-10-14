package com.swma.dnbn.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.GiftIconDetailActivity
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemGiftIcon
import kotlinx.android.synthetic.main.row_gifticon.view.*

class GiftIconAdapter(private val context: Activity, private val items: ArrayList<ItemGiftIcon>):RecyclerView.Adapter<GiftIconAdapter.ItemRowHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_gifticon, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                name.text = item.name
                Picasso.get().load(item.img).into(img)


                cardView.setOnClickListener {
                    val intent = Intent(context, GiftIconDetailActivity::class.java)
                    intent.putExtra("gifticon", item)
                    context.startActivity(intent)
                }
            }
        }
    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.textGifticonTitle
        val img = view.image
        val cardView = view.gifticonCardView
    }

}