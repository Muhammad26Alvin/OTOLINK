package com.example.uastam.ui.kategori

import java.io.Serializable

data class DataClassMotor(
    var imageUri: String? = null,
    var harga: String = "",
    var deskripsi: String = "",
    var tahun: String = "",
    var lokasi: String = "",
    var merk: String = "",
    var tipe: String = "",
    var jarak: String = "",
    var warna: String = "",
    var transmisi: String = "",
    var sertifikasi: String = "",
    var alamat: String = "",
    var penjual: String = "",
    var isFavorite: Boolean = false
) : Serializable {

    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", false)
}
