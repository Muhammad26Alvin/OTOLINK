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

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val editPassword: EditText = view.findViewById(R.id.editkatasandi)
        val togglePassword: ImageView = view.findViewById(R.id.password)

        val repeatPassword: EditText = view.findViewById(R.id.repeatkatasandi)
        val toggleRepeatPassword: ImageView = view.findViewById(R.id.repeatpassword)

        val btnSimpan: View = view.findViewById(R.id.btnkatasandi)

        var isPasswordVisible = false
        var isRepeatVisible = false

        togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                togglePassword.setImageResource(R.drawable.ic_eye)
            } else {
                editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                togglePassword.setImageResource(R.drawable.ic_eye_close)
            }
            editPassword.setSelection(editPassword.text.length)
        }

        toggleRepeatPassword.setOnClickListener {
            isRepeatVisible = !isRepeatVisible
            if (isRepeatVisible) {
                repeatPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                toggleRepeatPassword.setImageResource(R.drawable.ic_eye)
            } else {
                repeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                toggleRepeatPassword.setImageResource(R.drawable.ic_eye_close)
            }
            repeatPassword.setSelection(repeatPassword.text.length)
        }

        btnSimpan.setOnClickListener {
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

            val sharedPref = requireActivity().getSharedPreferences("user_prefs", 0)
            with(sharedPref.edit()) {
                putString("password", newPassword)
                apply()
            }

            Toast.makeText(requireContext(), "Password berhasil disimpan", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
    }
}
