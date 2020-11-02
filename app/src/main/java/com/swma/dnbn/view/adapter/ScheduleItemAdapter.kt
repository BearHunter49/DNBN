package com.swma.dnbn.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.view.activity.ChannelActivity
import com.swma.dnbn.R
import com.swma.dnbn.model.item.ItemSchedule
import kotlinx.android.synthetic.main.row_schedule_item.view.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ScheduleItemAdapter(val context: Activity, val items: ArrayList<ItemSchedule>): RecyclerView.Adapter<ScheduleItemAdapter.ItemRowHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_schedule_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                title.text = item.scheduleTitle
                user.text = item.scheduleUserName
                Picasso.get().load(item.scheduleImageUrl).into(img)

                // '2019-09-25T23:25:00' 형식
                val dateAndTime = item.scheduleDate.split("T")
                val time = LocalTime.parse(dateAndTime[1])
                date.text = time.format(DateTimeFormatter.ofPattern("HH시 mm분"))

                cardView.setOnClickListener {
                    val intent = Intent(context, ChannelActivity::class.java)
                    intent.putExtra("userId", item.scheduleUserId)
                    context.startActivity(intent)
                }

            }
        }


    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val date = view.textSchedule
        val img = view.scheduleImageView
        val title = view.textScheduleTitle
        val user = view.textScheduleUser
        val cardView = view.scheduleCardView
    }



}