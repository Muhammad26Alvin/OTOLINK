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
import com.example.uastam.LoginActivity
import com.example.uastam.R
import com.example.uastam.ui.account.EditProfileFragment
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod


class RegisterFragment : Fragment() {

    private lateinit var formEmailRegister: EditText
    private lateinit var formUsernameRegister: EditText
    private lateinit var formPasswordRegister: EditText
    private lateinit var formConfirmPasswordRegister: EditText
    private lateinit var btnKonfirmasiRegister: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formEmailRegister = view.findViewById(R.id.formemailregister)
        formUsernameRegister = view.findViewById(R.id.formusernameregister)
        formPasswordRegister = view.findViewById(R.id.formpasswordregister)
        formConfirmPasswordRegister = view.findViewById(R.id.formconfirmpasswordregister)
        btnKonfirmasiRegister = view.findViewById(R.id.btnkonfirmasiregister)
        val eyePassword: ImageView = view.findViewById(R.id.eyepasswordregister)
        val eyeConfirmPassword: ImageView = view.findViewById(R.id.eyeconfirmpasswordregister)
        val btnBack: ImageView = view.findViewById(R.id.btnbackregister)

        var isPasswordVisible = false
        var isConfirmPasswordVisible = false

        eyePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                formPasswordRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye)
            } else {
                formPasswordRegister.transformationMethod = PasswordTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye_close)
            }
            formPasswordRegister.setSelection(formPasswordRegister.text.length)
        }

        eyeConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                formConfirmPasswordRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeConfirmPassword.setImageResource(R.drawable.ic_eye)
            } else {
                formConfirmPasswordRegister.transformationMethod = PasswordTransformationMethod.getInstance()
                eyeConfirmPassword.setImageResource(R.drawable.ic_eye_close)
            }
            formConfirmPasswordRegister.setSelection(formConfirmPasswordRegister.text.length)
        }

        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnKonfirmasiRegister.setOnClickListener {
            val email = formEmailRegister.text.toString()
            val username = formUsernameRegister.text.toString()
            val password = formPasswordRegister.text.toString()
            val confirmPassword = formConfirmPasswordRegister.text.toString()

            // Validasi sederhana
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("users")

            val helperclass = Helperclass(email, username, password, confirmPassword)
            reference.child(username).setValue(helperclass)

            Toast.makeText(requireContext(), "Sign Up Berhasil", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
