package com.example.uastam.ui.itempesanan

data class Pesanan(
    val nama: String,
    val lokasi: String,
    val harga: String,
    val gambarResId: Int,
    val status: String  // tambahan untuk filter tab
)
