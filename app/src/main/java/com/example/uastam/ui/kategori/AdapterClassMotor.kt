package com.example.uastam.ui.kategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uastam.R

class AdapterClassMotor(
    private var datalist: MutableList<DataClassMotor>,
    private val onItemClick: (DataClassMotor) -> Unit
) : RecyclerView.Adapter<AdapterClassMotor.ViewHolderClass>() {

    fun updateData(newList: List<DataClassMotor>) {
        datalist = newList.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvmImage: ImageView = itemView.findViewById(R.id.gambarkategorimotor)
        val rvmTitle: TextView = itemView.findViewById(R.id.hargakategorimotor)
        val rvmDesc: TextView = itemView.findViewById(R.id.deskripsikategorimotor)
        val rvmYear: TextView = itemView.findViewById(R.id.tahunkategorimotor)
        val rvmLoc: TextView = itemView.findViewById(R.id.lokasikategorimotor)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.icon_favorite1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori_motor, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = datalist[position]

        Glide.with(holder.itemView.context)
            .load(item.imageUri)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.rvmImage)

        holder.rvmTitle.text = item.harga
        holder.rvmDesc.text = item.deskripsi
        holder.rvmYear.text = item.tahun
        holder.rvmLoc.text = item.lokasi

        val iconRes = if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_love
        holder.favoriteIcon.setImageResource(iconRes)

        holder.favoriteIcon.setOnClickListener {
            item.isFavorite = !item.isFavorite
            notifyItemChanged(position)
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = datalist.size
}
