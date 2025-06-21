package com.example.uastam.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.uastam.R
import android.content.Intent
import android.content.Context
import com.example.uastam.LoginActivity

class PengaturanFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengaturan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val cardsandi: View = view.findViewById(R.id.buatkatasandi)
        cardsandi.setOnClickListener {
            val katasandi = KatasandiFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, katasandi)
                .addToBackStack(null)
                .commit()
        }

        val logoutCard: View = view.findViewById(R.id.pengaturanlogout)
        logoutCard.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear()
                apply()
            }

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}