package com.example.uastam.ui.kategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R

class AdapterClassMobil(
    private var datalist: MutableList<DataClassMobil>,
    private val onItemClick: (DataClassMobil) -> Unit
) : RecyclerView.Adapter<AdapterClassMobil.ViewHolderClass>() {

    fun updateData(newList: List<DataClassMobil>) {
        datalist = newList.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvmImage: ImageView = itemView.findViewById(R.id.gambarkategorimobil)
        val rvmTitle: TextView = itemView.findViewById(R.id.hargakategorimobil)
        val rvmDesc: TextView = itemView.findViewById(R.id.deskripsikategorimobil)
        val rvmYear: TextView = itemView.findViewById(R.id.tahunkategorimobil)
        val rvmLoc: TextView = itemView.findViewById(R.id.lokasikategorimobil)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.icon_favorite1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori_mobil, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = datalist[position]
        holder.rvmImage.setImageResource(item.imageResId)
        holder.rvmTitle.text = item.harga
        holder.rvmDesc.text = item.deskripsi
        holder.rvmYear.text = item.tahun
        holder.rvmLoc.text = item.lokasi

        // Atur icon favorit berdasarkan status
        val iconRes = if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_love
        holder.favoriteIcon.setImageResource(iconRes)

        // Toggle favorit saat diklik
        holder.favoriteIcon.setOnClickListener {
            item.isFavorite = !item.isFavorite
            notifyItemChanged(position)
        }

        // Navigasi ke detail saat item diklik
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = datalist.size
}
