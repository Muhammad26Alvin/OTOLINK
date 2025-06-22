package com.example.uastam.ui.kategori

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class DataClassMotor(
    val imageResId: Int,
    val harga: String,
    val deskripsi: String,
    val tahun: String,
    val lokasi: String,
    val merkModel: String,
    val tipe: String,
    val kilometer: String,
    val warna: String,
    val transmisi: String,
    val sertifikasi: String,
    val alamatLokasi: String,
    val namapenjual: String,
    var isFavorite : Boolean = false
) : java.io.Serializable

