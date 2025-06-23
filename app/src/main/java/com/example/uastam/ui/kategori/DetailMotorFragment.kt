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

class DetailMotorFragment : Fragment() {

    companion object {
        private const val ARG_MOTOR = "arg_motor"

        fun newInstance(motor: DataClassMotor): DetailMotorFragment {
            val fragment = DetailMotorFragment()
            val args = Bundle()
            args.putSerializable(ARG_MOTOR, motor)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomBar = requireActivity().findViewById<LinearLayout>(R.id.bottombardetail)
        bottomBar?.visibility = View.VISIBLE
    }


    private lateinit var motor: DataClassMotor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        motor = arguments?.getSerializable(ARG_MOTOR) as DataClassMotor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_motor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.gambardetailmotor)
        Glide.with(this)
            .load(motor.imageUri)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)

        view.findViewById<TextView>(R.id.hargamotor).text = motor.harga
        view.findViewById<TextView>(R.id.deskripsimotor).text = motor.deskripsi
        view.findViewById<TextView>(R.id.spesifikasimotor).text = motor.tahun
        view.findViewById<TextView>(R.id.lokasimotor).text = motor.alamat
        view.findViewById<TextView>(R.id.tipedetailmotor).text = motor.tipe
        view.findViewById<TextView>(R.id.merkdetailmotor).text = motor.merk
        view.findViewById<TextView>(R.id.tahundetailmotor).text = motor.tahun
        view.findViewById<TextView>(R.id.kilometerdetailmotor).text = motor.jarak
        view.findViewById<TextView>(R.id.warnadetailmotor).text = motor.warna
        view.findViewById<TextView>(R.id.transmisidetailmotor).text = motor.transmisi
        view.findViewById<TextView>(R.id.sertifikasidetailmotor).text = motor.sertifikasi
        view.findViewById<TextView>(R.id.lokasidetailmotor).text = motor.alamat
        view.findViewById<TextView>(R.id.namapenjualmotor).text = motor.penjual

        // Tombol Kembali
        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Tombol Chat ke Penjual
        view.findViewById<View>(R.id.btnChatPenjual).setOnClickListener {
            val chatName = motor.penjual  // pastikan ini bukan null atau kosong

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
