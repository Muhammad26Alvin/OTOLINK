package com.example.uastam.ui.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.uastam.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class EditProfileFragment : Fragment() {

    private lateinit var ivProfile: ImageView
    private lateinit var etUsername: EditText
    private lateinit var etInfo: EditText
    private lateinit var etNomorTelepon: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSimpan: TextView
    private lateinit var database: DatabaseReference

    private val PICK_IMAGE_REQUEST = 1001
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfile = view.findViewById(R.id.imgProfile)
        etUsername = view.findViewById(R.id.edittextusername)
        etInfo = view.findViewById(R.id.edittextinfo)
        etNomorTelepon = view.findViewById(R.id.editnomortelepon)
        etEmail = view.findViewById(R.id.textEmail)
        btnSimpan = view.findViewById(R.id.btnsimpan)

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        database = FirebaseDatabase.getInstance().getReference("users")

        ivProfile.setOnClickListener {
            openGalleryForImage()
        }

        loadProfile()

        btnSimpan.setOnClickListener {
            saveProfile()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                ivProfile.setImageURI(imageUri)
            }
        }
    }

    private fun saveProfile() {
        val newUsername = etUsername.text.toString().trim()
        val info = etInfo.text.toString().trim()
        val nomorTelepon = etNomorTelepon.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (newUsername.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Username dan Email harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val oldUsername = sharedPref.getString("username", null) ?: newUsername

        // Simpan ke SharedPreferences
        with(sharedPref.edit()) {
            putString("username", newUsername)
            putString("info", info)
            putString("nomorTelepon", nomorTelepon)
            putString("email", email)
            if (imageUri != null) {
                putString("profileImageUri", imageUri.toString())
            }
            apply()
        }

        database.child(oldUsername).get().addOnSuccessListener { snapshot ->
            val password = snapshot.child("password").value?.toString() ?: ""
            val confirmPassword = snapshot.child("confirmpassword").value?.toString() ?: ""

            val updatedUser = mapOf(
                "username" to newUsername,
                "email" to email,
                "info" to info,
                "nomorTelepon" to nomorTelepon,
                "password" to password,
                "confirmpassword" to confirmPassword
            )

            if (oldUsername != newUsername) {
                database.child(oldUsername).removeValue()
            }

            database.child(newUsername).setValue(updatedUser)
                .addOnSuccessListener {
                    if (!isAdded) return@addOnSuccessListener

                    val resultBundle = Bundle().apply {
                        putString("newName", newUsername)
                        putString("newImageUri", imageUri?.toString())
                    }
                    parentFragmentManager.setFragmentResult("requestKeyName", resultBundle)

                    Toast.makeText(requireContext(), "Data Firebase disimpan", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                .addOnFailureListener {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Gagal simpan ke Firebase", Toast.LENGTH_SHORT).show()
                    }
                }
        }.addOnFailureListener {
            if (isAdded) {
                Toast.makeText(requireContext(), "Gagal ambil data lama", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadProfile() {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)

        if (username.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        database.child(username).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                etUsername.setText(snapshot.child("username").value?.toString() ?: "")
                etInfo.setText(snapshot.child("info").value?.toString() ?: "")
                etNomorTelepon.setText(snapshot.child("nomorTelepon").value?.toString() ?: "")
                etEmail.setText(snapshot.child("email").value?.toString() ?: "")

                val imageUriString = sharedPref.getString("profileImageUri", null)
                if (!imageUriString.isNullOrEmpty()) {
                    imageUri = Uri.parse(imageUriString)
                    try {
                        ivProfile.setImageURI(imageUri)
                    } catch (e: SecurityException) {
                        Toast.makeText(requireContext(), "Gagal memuat gambar. Coba pilih ulang.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Data tidak ditemukan di Firebase", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal ambil data dari Firebase", Toast.LENGTH_SHORT).show()
        }
    }
}
