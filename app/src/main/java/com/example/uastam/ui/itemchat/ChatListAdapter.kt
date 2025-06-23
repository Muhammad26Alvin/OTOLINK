package com.example.uastam.ui.itemchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R

class ChatListAdapter(
    private var list: List<ChatItem>,
    private val onItemClick: (ChatItem) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namaText: TextView = view.findViewById(R.id.namaText)
        val root: View = view

        fun bind(item: ChatItem) {
            namaText.text = item.nama
            root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_chat, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<ChatItem>) {
        list = newList
        notifyDataSetChanged()
    }
}

