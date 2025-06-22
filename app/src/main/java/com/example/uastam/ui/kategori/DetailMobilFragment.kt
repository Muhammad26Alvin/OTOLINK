package com.example.uastam.ui.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uastam.R

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

        view.findViewById<ImageView>(R.id.gambardetailmobil).setImageResource(mobil.imageResId)
        view.findViewById<TextView>(R.id.hargamobil).text = mobil.harga
        view.findViewById<TextView>(R.id.deskripsimobil).text = mobil.deskripsi
        view.findViewById<TextView>(R.id.spesifikasimobil).text = mobil.tahun
        view.findViewById<TextView>(R.id.lokasimobil).text = mobil.lokasi
        view.findViewById<TextView>(R.id.tipedetailmobil).text = mobil.tipe
        view.findViewById<TextView>(R.id.merkdetailmobil).text = mobil.merkModel
        view.findViewById<TextView>(R.id.tahundetailmobil).text = mobil.tahun
        view.findViewById<TextView>(R.id.kilometerdetailmobil).text = mobil.kilometer
        view.findViewById<TextView>(R.id.warnadetailmobil).text = mobil.warna
        view.findViewById<TextView>(R.id.transmisidetailmobil).text = mobil.transmisi
        view.findViewById<TextView>(R.id.sertifikasidetailmobil).text = mobil.sertifikasi
        view.findViewById<TextView>(R.id.lokasidetailmobil).text = mobil.alamatLokasi
        view.findViewById<TextView>(R.id.namapenjualmobil).text = mobil.namapenjual


        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
