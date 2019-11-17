package com.swma.dnbn.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swma.dnbn.R
import com.swma.dnbn.item.ItemChat
import kotlinx.android.synthetic.main.row_chat_item.view.*

class ChatAdapter(private val context: Activity):RecyclerView.Adapter<ChatAdapter.ItemRowHolder>(){

    private val items = arrayListOf<ItemChat>()
    private val SIZE = 15

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_chat_item, parent, false)
        return ItemRowHolder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        items[position].let { item ->
            with(holder){
                textId.text = item.id
                textText.text = item.text
            }
        }

    }

    // 채팅 내용 추가
    fun addItem(chat: ItemChat){
        items.add(0, chat)

        // 사이즈 초과 시
        if (itemCount > SIZE){
            items.removeAt(SIZE)
            notifyItemRemoved(SIZE)
        }
        notifyItemInserted(0)

    }


    inner class ItemRowHolder(view: View): RecyclerView.ViewHolder(view) {
        val textId = view.textChatId
        val textText = view.textChatText
    }


}