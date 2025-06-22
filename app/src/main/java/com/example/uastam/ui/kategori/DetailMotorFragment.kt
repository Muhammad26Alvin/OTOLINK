package com.example.uastam.ui.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uastam.R

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

        view.findViewById<ImageView>(R.id.gambardetailmotor).setImageResource(motor.imageResId)
        view.findViewById<TextView>(R.id.hargamotor).text = motor.harga
        view.findViewById<TextView>(R.id.deskripsimotor).text = motor.deskripsi
        view.findViewById<TextView>(R.id.spesifikasimotor).text = motor.tahun
        view.findViewById<TextView>(R.id.lokasimotor).text = motor.lokasi
        view.findViewById<TextView>(R.id.tipedetailmotor).text = motor.tipe
        view.findViewById<TextView>(R.id.merkdetailmotor).text = motor.merkModel
        view.findViewById<TextView>(R.id.tahundetailmotor).text = motor.tahun
        view.findViewById<TextView>(R.id.kilometerdetailmotor).text = motor.kilometer
        view.findViewById<TextView>(R.id.warnadetailmotor).text = motor.warna
        view.findViewById<TextView>(R.id.transmisidetailmotor).text = motor.transmisi
        view.findViewById<TextView>(R.id.sertifikasidetailmotor).text = motor.sertifikasi
        view.findViewById<TextView>(R.id.lokasidetailmotor).text = motor.alamatLokasi
        view.findViewById<TextView>(R.id.namapenjualmotor).text = motor.namapenjual


        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
