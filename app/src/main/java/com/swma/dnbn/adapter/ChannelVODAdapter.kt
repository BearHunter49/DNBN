package com.swma.dnbn.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.row_channel_vod.view.*

class ChannelVODAdapter (private val context: Activity, private val items: ArrayList<ItemVOD>): RecyclerView.Adapter<ChannelVODAdapter.ItemRowHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_channel_vod, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                Picasso.get().load(item.vodThumbnailUrl).into(img)
                title.text = item.vodTitle
                date.text = item.vodDate
                viewer.text = item.vodViewer.toString()
            }
        }
    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val img = view.imgChannelVOD
        val title = view.channelVODTitle
        val date = view.channelVODDate
        val viewer = view.channelViewer
    }

}