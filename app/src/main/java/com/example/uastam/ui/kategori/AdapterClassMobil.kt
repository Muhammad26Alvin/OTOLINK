package com.example.uastam.ui.kategori

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uastam.R
import com.google.firebase.database.FirebaseDatabase

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori_mobil, parent, false)
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
            val isFavorited = !item.isFavorite
            item.isFavorite = isFavorited
            notifyItemChanged(position)

            val context = holder.itemView.context
            val sharedPref = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            val username = sharedPref.getString("username", null)

            if (username.isNullOrEmpty()) {
                Toast.makeText(context, "User belum login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbRef = FirebaseDatabase.getInstance().getReference("favorit/$username")
            val key = item.judul ?: dbRef.push().key ?: return@setOnClickListener

            if (isFavorited) {
                dbRef.child(key).setValue(item.copy(isFavorite = true))
            } else {
                dbRef.child(key).removeValue()
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = datalist.size
}
