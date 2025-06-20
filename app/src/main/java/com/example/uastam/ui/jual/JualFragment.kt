package com.example.uastam.ui.jual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uastam.MainActivity
import com.example.uastam.R
import com.example.uastam.ui.sampel.Sampel4Fragment

class JualFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell?.isEnabled = true
        fabSell?.alpha = 1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.showBottomBar(true)

        val mobil = view.findViewById<View>(R.id.kategorimobil)
        mobil.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout, FormInputFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val motor = view.findViewById<View>(R.id.kategorimotor)
        motor.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout, FormInputFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
