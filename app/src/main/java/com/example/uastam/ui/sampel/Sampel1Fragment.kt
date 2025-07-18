package com.example.uastam.ui.sampel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uastam.R
import com.example.uastam.ui.kategori.DataClassMobil

class Sampel1Fragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = false
        fabSell.alpha = 0.5f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sampel1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Tombol back
        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
