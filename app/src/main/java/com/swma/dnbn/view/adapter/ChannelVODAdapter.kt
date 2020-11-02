package com.swma.dnbn.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.view.activity.VODWatchActivity
import com.swma.dnbn.model.item.ItemVOD
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

                val dateList = item.vodDate.split("T")
                date.text = dateList[0]

                viewer.text = item.vodViewer.toString()

                constView.setOnClickListener {
                    val intent = Intent(context, VODWatchActivity::class.java)
                    intent.putExtra("vod", item)
                    context.startActivity(intent)
                }

            }
        }
    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val img = view.imgChannelVOD
        val title = view.channelVODTitle
        val date = view.channelVODDate
        val viewer = view.channelViewer
        val constView = view.lytChannelVOD
    }

}