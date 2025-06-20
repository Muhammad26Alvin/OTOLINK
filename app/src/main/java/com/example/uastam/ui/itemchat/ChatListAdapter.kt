package com.example.uastam.ui.itemchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R

class ChatListAdapter(
    private var chatList: List<ChatItem>,
    private val onItemClick: (ChatItem) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama: TextView = itemView.findViewById(R.id.namaText)
        private val pesan: TextView = itemView.findViewById(R.id.pesanText)
        private val avatar: ImageView = itemView.findViewById(R.id.avatar)

        fun bind(item: ChatItem) {
            nama.text = item.nama
            pesan.text = item.pesanTerakhir
            avatar.setImageResource(item.avatarResId)
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    fun updateData(newList: List<ChatItem>) {
        chatList = newList
        notifyDataSetChanged()
    }
}
