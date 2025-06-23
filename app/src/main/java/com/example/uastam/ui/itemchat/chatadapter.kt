package com.example.uastam.ui.itemchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R

class chatadapter(
    private var messageList: List<Message>,
    private val currentUser: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LEFT = 0
        private const val VIEW_TYPE_RIGHT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].sender == currentUser) VIEW_TYPE_RIGHT else VIEW_TYPE_LEFT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_bubble_right, parent, false)
            RightViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_bubble_left, parent, false)
            LeftViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder is RightViewHolder) holder.bind(message)
        else if (holder is LeftViewHolder) holder.bind(message)
    }

    override fun getItemCount(): Int = messageList.size

    fun setMessages(messages: List<Message>) {
        this.messageList = messages
        notifyDataSetChanged()
    }

    class LeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)
        fun bind(message: Message) {
            textMessage.text = message.text
        }
    }

    class RightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)
        fun bind(message: Message) {
            textMessage.text = message.text
        }
    }
}

