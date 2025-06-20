package com.example.uastam.ui.account

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.uastam.R

class AccountFragment : Fragment() {

    private lateinit var tvUserName: TextView
    private lateinit var ivProfile: ImageView

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
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUserName = view.findViewById(R.id.username)
        ivProfile = view.findViewById(R.id.profile)  // Pastikan ImageView dengan id ini ada di layout

        loadUserData()

        // Setup listener untuk menerima update nama dan foto dari EditProfileFragment
        parentFragmentManager.setFragmentResultListener("requestKeyName", this) { _, bundle ->
            val updatedName = bundle.getString("newName")
            val updatedImageUriString = bundle.getString("newImageUri")

            if (!updatedName.isNullOrEmpty()) {
                tvUserName.text = updatedName
            }
            if (!updatedImageUriString.isNullOrEmpty()) {
                ivProfile.setImageURI(Uri.parse(updatedImageUriString))
            }
        }

        val btnProfil: Button = view.findViewById(R.id.btnprofil)
        btnProfil.setOnClickListener {
            val editProfileFragment = EditProfileFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, editProfileFragment)
                .addToBackStack(null)
                .commit()
        }

        val pesananProfil: View = view.findViewById(R.id.profilpesanan)
        pesananProfil.setOnClickListener {
            val pesananFragment = PesananFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, pesananFragment) // Ganti dengan ID container layout-mu
                .addToBackStack(null)
                .commit()
        }

        val pengaturanProfil: View = view.findViewById(R.id.profilpengaturan)
        pengaturanProfil.setOnClickListener {
            val pengaturanFragment = PengaturanFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, pengaturanFragment) // Ganti dengan ID container layout-mu
                .addToBackStack(null)
                .commit()
        }


        val PusatBantuanProfil: View = view.findViewById(R.id.profilpusatbantuan)
        PusatBantuanProfil.setOnClickListener {
            val pusatbantuanFragment = PusatBantuanFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, pusatbantuanFragment) // Ganti dengan ID container layout-mu
                .addToBackStack(null)
                .commit()
        }

    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("nama", "onyourmark")
        val savedImageUriString = sharedPref.getString("profileImageUri", null)

        tvUserName.text = savedName
        if (!savedImageUriString.isNullOrEmpty()) {
            ivProfile.setImageURI(Uri.parse(savedImageUriString))
        }
    }
}
