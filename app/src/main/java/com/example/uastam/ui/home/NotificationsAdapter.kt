package com.example.uastam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.ui.home.Notifications

class NotifikasiAdapter(private val listNotifikasi: List<Notifications>) :
    RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.namanotif)
        val pesan: TextView = itemView.findViewById(R.id.pesannotif)
        val waktu: TextView = itemView.findViewById(R.id.waktunotif)
        val avatar: ImageView = itemView.findViewById(R.id.avatarnotif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notifikasi = listNotifikasi[position]
        holder.nama.text = notifikasi.nama
        holder.pesan.text = notifikasi.pesan
        holder.waktu.text = notifikasi.waktu
        holder.avatar.setImageResource(notifikasi.avatarResId)
    }

    override fun getItemCount(): Int = listNotifikasi.size
}
