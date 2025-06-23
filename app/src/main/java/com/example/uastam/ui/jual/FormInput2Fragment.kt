package com.example.uastam.ui.jual

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.uastam.ImgurUploader
import com.example.uastam.MainActivity
import com.example.uastam.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase

class FormInput2Fragment : Fragment() {

    private lateinit var judulInput: EditText
    private lateinit var deskripsiInput: EditText
    private lateinit var alamatInput: EditText
    private lateinit var hargaInput: EditText
    private lateinit var btnLanjut: MaterialButton
    private lateinit var closeBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_form_input2, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.showBottomBar(false)

        // Bind View
        judulInput = view.findViewById(R.id.judulbarang)
        deskripsiInput = view.findViewById(R.id.deskripsibarang)
        alamatInput = view.findViewById(R.id.alamatbarang)
        hargaInput = view.findViewById(R.id.hargabarang)
        btnLanjut = view.findViewById(R.id.btnform2)
        closeBtn = view.findViewById(R.id.close)

        closeBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Ambil data dari Fragment sebelumnya
        val args = arguments
        val tipe = args?.getString("tipe")
        val merk = args?.getString("merk")
        val tahun = args?.getString("tahun")
        val jarak = args?.getString("jarak")
        val warna = args?.getString("warna")
        val transmisi = args?.getString("transmisi")
        val sertifikasi = args?.getString("sertifikasi")
        val kategori = if (tipe.equals("Motor", ignoreCase = true)) "motor" else "mobil"
        val imageUriString = args?.getString("imageUri")

        btnLanjut.setOnClickListener {
            val judul = judulInput.text.toString().trim()
            val deskripsi = deskripsiInput.text.toString().trim()
            val alamat = alamatInput.text.toString().trim()
            val hargaText = hargaInput.text.toString().trim()
            val harga = if (hargaText.startsWith("Rp")) hargaText else "Rp $hargaText"

            if (judul.isEmpty() || deskripsi.isEmpty() || alamat.isEmpty() || hargaText.isEmpty()) {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imageUri = imageUriString?.let { Uri.parse(it) }
            if (imageUri == null) {
                Toast.makeText(requireContext(), "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil nama user dari SharedPreferences
            val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            val username = sharedPref.getString("username", null)
            if (username.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Langsung upload data
            uploadData(judul, deskripsi, alamat, harga, tipe, merk, tahun, jarak, warna, transmisi, sertifikasi, kategori, imageUri, username)
        }
    }

    private fun uploadData(
        judul: String,
        deskripsi: String,
        alamat: String,
        harga: String,
        tipe: String?,
        merk: String?,
        tahun: String?,
        jarak: String?,
        warna: String?,
        transmisi: String?,
        sertifikasi: String?,
        kategori: String,
        imageUri: Uri,
        namaUser: String
    ) {
        btnLanjut.isEnabled = false
        btnLanjut.text = "Mengunggah gambar..."

        ImgurUploader.uploadImage(requireContext(), imageUri) { imageUrl ->
            requireActivity().runOnUiThread {
                btnLanjut.isEnabled = true
                btnLanjut.text = "Simpan"

                if (imageUrl == null) {
                    Toast.makeText(requireContext(), "Gagal upload gambar", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }

                val data = hashMapOf<String, Any?>(
                    "judul" to judul,
                    "deskripsi" to deskripsi,
                    "alamat" to alamat,
                    "harga" to harga,
                    "tipe" to tipe,
                    "merk" to merk,
                    "tahun" to tahun,
                    "jarak" to jarak,
                    "warna" to warna,
                    "transmisi" to transmisi,
                    "sertifikasi" to sertifikasi,
                    "kategori" to kategori,
                    "imageUri" to imageUrl,
                    "penjual" to namaUser
                )

                val dbRef = FirebaseDatabase.getInstance().getReference("jualan/${kategori.lowercase()}")
                val key = dbRef.push().key
                key?.let {
                    dbRef.child(it).setValue(data).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Barang berhasil diunggah!", Toast.LENGTH_SHORT).show()
                        requireActivity().onBackPressed()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Gagal unggah: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
