package com.example.uastam.ui.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.uastam.R
import com.example.uastam.ui.itemchat.ChatDetailFragment
import com.google.android.material.button.MaterialButton

class DetailMobilFragment : Fragment() {

    companion object {
        private const val ARG_MOBIL = "arg_mobil"

        fun newInstance(mobil: DataClassMobil): DetailMobilFragment {
            val fragment = DetailMobilFragment()
            val args = Bundle()
            args.putSerializable(ARG_MOBIL, mobil)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomBar = requireActivity().findViewById<LinearLayout>(R.id.bottombardetail)
        bottomBar?.visibility = View.VISIBLE
    }


    private lateinit var mobil: DataClassMobil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobil = arguments?.getSerializable(ARG_MOBIL) as DataClassMobil
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_mobil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.gambardetailmobil)
        Glide.with(this)
            .load(mobil.imageUri)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)

        view.findViewById<TextView>(R.id.hargamobil).text = mobil.harga
        view.findViewById<TextView>(R.id.deskripsimobil).text = mobil.deskripsi
        view.findViewById<TextView>(R.id.spesifikasimobil).text = mobil.tahun
        view.findViewById<TextView>(R.id.lokasimobil).text = mobil.alamat
        view.findViewById<TextView>(R.id.tipedetailmobil).text = mobil.tipe
        view.findViewById<TextView>(R.id.merkdetailmobil).text = mobil.merk
        view.findViewById<TextView>(R.id.tahundetailmobil).text = mobil.tahun
        view.findViewById<TextView>(R.id.kilometerdetailmobil).text = mobil.jarak
        view.findViewById<TextView>(R.id.warnadetailmobil).text = mobil.warna
        view.findViewById<TextView>(R.id.transmisidetailmobil).text = mobil.transmisi
        view.findViewById<TextView>(R.id.sertifikasidetailmobil).text = mobil.sertifikasi
        view.findViewById<TextView>(R.id.lokasidetailmobil).text = mobil.alamat
        view.findViewById<TextView>(R.id.namapenjualmobil).text = mobil.penjual

        // Tombol Kembali
        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Tombol Chat ke Penjual
        view.findViewById<View>(R.id.btnChatPenjual).setOnClickListener {
            val chatName = mobil.penjual  // pastikan ini bukan null atau kosong

            // Sembunyikan bottom bar jika ada
            val bottomBar = requireActivity().findViewById<LinearLayout>(R.id.bottombardetail)
            bottomBar?.visibility = View.GONE

            // Pindah ke ChatDetailFragment
            val chatDetailFragment = ChatDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("NAMA_CHAT", chatName)
                }
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framecontainer, chatDetailFragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
