package com.swma.dnbn.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swma.dnbn.R
import com.swma.dnbn.model.item.ItemSchedule
import kotlinx.android.synthetic.main.home_row_schedule_item.view.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class HomeScheduleAdapter(private val context: Context, private val items:ArrayList<ItemSchedule>): RecyclerView.Adapter<HomeScheduleAdapter.ItemRowHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_row_schedule_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                title.text = item.scheduleTitle
                user.text = item.scheduleUserName

                // '2019-09-25T23:25:00' 형식
                val dateAndTime = item.scheduleDate.split("T")
                val time = LocalTime.parse(dateAndTime[1])
                date.text = time.format(DateTimeFormatter.ofPattern("HH시 mm분"))

                Picasso.get().load(item.scheduleImageUrl).into(image)

                cardView.setOnClickListener {
                    Toast.makeText(context, item.scheduleTitle + " Clicked!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.textScheduleTitle
        val date = view.textSchedule
        val user = view.textScheduleUser
        val image = view.scheduleImageView
        val cardView = view.scheduleCardView
    }

}