package com.example.uastam.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uastam.R
import com.example.uastam.ui.sampel.Sampel1Fragment // Sesuaikan dengan lokasi filenya
import com.example.uastam.ui.sampel.Sampel2Fragment
import com.example.uastam.ui.sampel.Sampel3Fragment
import com.example.uastam.ui.sampel.Sampel4Fragment
import com.example.uastam.ui.sampel.Sampel5Fragment
import com.example.uastam.ui.sampel.Sampel6Fragment
import com.example.uastam.ui.sampel.Sampel7Fragment
import com.example.uastam.ui.sampel.Sampel8Fragment
import com.example.uastam.ui.sampel.Sampel9Fragment

class HomeFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Alternatif aman (ViewBinding direkomendasikan di proyek modern)
        val id1 = view.findViewById<View>(R.id.Item1)
        id1.setOnClickListener {
            // Ganti fragment ke Sampel1Fragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel1Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val id2 = view.findViewById<View>(R.id.Item2)
        id2.setOnClickListener {
            // Ganti fragment ke Sampel1Fragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel2Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val id3 = view.findViewById<View>(R.id.Item3)
        id3.setOnClickListener {
            // Ganti fragment ke Sampel1Fragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel3Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val id4 = view.findViewById<View>(R.id.Item4)
        id4.setOnClickListener {
            // Ganti fragment ke Sampel1Fragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel4Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
