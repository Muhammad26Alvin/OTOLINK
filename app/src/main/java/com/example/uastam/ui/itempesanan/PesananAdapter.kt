package com.example.uastam.ui.itempesanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R

class PesananAdapter(
    private var pesananList: List<Pesanan>,
    private val onItemClick: ((Pesanan) -> Unit)? = null
) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    inner class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPesanan: ImageView = itemView.findViewById(R.id.imgPesanan)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)

        fun bind(pesanan: Pesanan) {
            imgPesanan.setImageResource(pesanan.gambarResId)
            tvNama.text = pesanan.nama
            tvLokasi.text = pesanan.lokasi
            tvHarga.text = pesanan.harga

            itemView.setOnClickListener {
                onItemClick?.invoke(pesanan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        holder.bind(pesananList[position])
    }

    override fun getItemCount(): Int = pesananList.size

    fun updateData(newList: List<Pesanan>) {
        pesananList = newList
        notifyDataSetChanged()
    }
}
