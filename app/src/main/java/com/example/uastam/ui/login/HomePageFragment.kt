package com.example.uastam.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
        val eyePassword: ImageView = view.findViewById(R.id.eyePasswordLogin)

        var isPasswordVisible = false

        eyePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                formPasswordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye)
            } else {
                formPasswordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye_close)
            }
            formPasswordLogin.setSelection(formPasswordLogin.text.length)
        }

        // Tombol kembali
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Referensi ke Firebase
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

            // Cek user dari Firebase
            database.orderByChild("email").equalTo(email)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        var userFound = false
                        var username = ""

                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(Helperclass::class.java)
                            if (user?.password == password) {
                                userFound = true
                                username = user.username ?: ""
                                break
                            }
                        }

                        if (userFound) {
                            Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()

                            // Simpan ke SharedPreferences
                            val sharedPref = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString("email", email)
                                putString("username", username)
                                putBoolean("isLoggedIn", true) // ✅ Tambahkan ini
                                apply()
                            }

                            // Pindah ke MainActivity
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

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
