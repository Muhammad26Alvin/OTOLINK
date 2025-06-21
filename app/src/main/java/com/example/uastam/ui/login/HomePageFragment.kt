package com.example.uastam.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uastam.Helperclass
import com.example.uastam.MainActivity
import com.example.uastam.R
import com.example.uastam.ui.home.HomeFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomePageFragment : Fragment() {

    private lateinit var formEmailLogin: EditText
    private lateinit var formPasswordLogin: EditText
    private lateinit var btnKonfirmasiLogin: Button
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formEmailLogin = view.findViewById(R.id.formemaillogin)
        formPasswordLogin = view.findViewById(R.id.formpasswordlogin)
        btnKonfirmasiLogin = view.findViewById(R.id.btnkonfirmasilogin)
        val btnBack: ImageView = view.findViewById(R.id.btnbacklogin)

        // Tombol kembali
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Referensi ke database
        database = FirebaseDatabase.getInstance().getReference("users")

        // Tombol login
        btnKonfirmasiLogin.setOnClickListener {
            val email = formEmailLogin.text.toString().trim()
            val password = formPasswordLogin.text.toString().trim()

            if (email.isEmpty()) {
                formEmailLogin.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                formPasswordLogin.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            // Cek user di Firebase
            database.orderByChild("email").equalTo(email)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        var userFound = false

                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(Helperclass::class.java)
                            if (user?.password == password) {
                                userFound = true
                                break
                            }
                        }

                        if (userFound) {
                            Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish() // opsional: supaya tidak bisa kembali ke halaman login

                        } else {
                            Toast.makeText(requireContext(), "Password salah", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e: Exception ->
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
