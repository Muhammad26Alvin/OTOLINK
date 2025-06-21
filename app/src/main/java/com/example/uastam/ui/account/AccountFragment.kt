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
import android.widget.Toast
import com.example.uastam.R
import com.google.firebase.database.*

class AccountFragment : Fragment() {

    private lateinit var tvUserName: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var database: DatabaseReference

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
        ivProfile = view.findViewById(R.id.profile)

        loadUserData()

        // Update nama & gambar saat kembali dari EditProfileFragment
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

        // Navigasi antar fragment
        val btnProfil: Button = view.findViewById(R.id.btnprofil)
        btnProfil.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.profilpesanan).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, PesananFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.profilpengaturan).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, PengaturanFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.profilpusatbantuan).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.editprofil, PusatBantuanFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        val savedImageUri = sharedPref.getString("profileImageUri", null)

        if (savedEmail != null) {
            database = FirebaseDatabase.getInstance().getReference("users")

            database.orderByChild("email").equalTo(savedEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val username = userSnapshot.child("username").getValue(String::class.java)
                                tvUserName.text = username ?: "User"
                            }
                        } else {
                            tvUserName.text = "Email tidak ditemukan"
                        }

                        // Setel gambar profil dari SharedPreferences setelah data Firebase dimuat
                        if (!savedImageUri.isNullOrEmpty()) {
                            try {
                                ivProfile.setImageURI(Uri.parse(savedImageUri))
                            } catch (e: SecurityException) {
                                Toast.makeText(requireContext(), "Gagal menampilkan gambar profil", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            tvUserName.text = "Belum login"
        }
    }
}
