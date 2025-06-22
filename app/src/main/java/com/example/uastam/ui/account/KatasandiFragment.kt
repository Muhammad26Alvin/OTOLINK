package com.example.uastam.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.EditText
import android.widget.Toast
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.example.uastam.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.content.Context


class KatasandiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_katasandi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance().getReference("users")

        val editPassword: EditText = view.findViewById(R.id.editkatasandi)
        val repeatPassword: EditText = view.findViewById(R.id.repeatkatasandi)
        val btnkatasandi: View = view.findViewById(R.id.btnkatasandi)
        val eyePassword: ImageView = view.findViewById(R.id.eyepassword)
        val eyeConfirmPassword: ImageView = view.findViewById(R.id.eyerepeatpassword)

        var isPasswordVisible = false
        var isConfirmPasswordVisible = false

        eyePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye)
            } else {
                editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                eyePassword.setImageResource(R.drawable.ic_eye_close)
            }
            editPassword.setSelection(editPassword.text.length)
        }

        eyeConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                repeatPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                eyeConfirmPassword.setImageResource(R.drawable.ic_eye)
            } else {
                repeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                eyeConfirmPassword.setImageResource(R.drawable.ic_eye_close)
            }
            repeatPassword.setSelection(repeatPassword.text.length)
        }

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnkatasandi.setOnClickListener {
            val newPassword = editPassword.text.toString()
            val repeatPass = repeatPassword.text.toString()

            if (newPassword.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(requireContext(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != repeatPass) {
                Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            val username = sharedPref.getString("username", null)

            if (username == null) {
                Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updates = mapOf(
                "password" to newPassword,
                "confirmpassword" to repeatPass
            )

            database.child(username).updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal mengubah password", Toast.LENGTH_SHORT).show()
                }
        }
    }
}