package com.example.uastam.ui.account

import ImgurResponse
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
import com.bumptech.glide.Glide
import com.example.uastam.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

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
        btnBack.setOnClickListener { requireActivity().onBackPressed() }

        database = FirebaseDatabase.getInstance().getReference("users")

        ivProfile.setOnClickListener { openGalleryForImage() }
        loadProfile()

        btnSimpan.setOnClickListener { saveProfile() }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            ivProfile.setImageURI(imageUri)
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

        if (imageUri != null && imageUri!!.scheme?.startsWith("content") == true) {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if (bytes != null) {
                val requestFile = MultipartBody.Part.createFormData(
                    "image", "profile.jpg",
                    bytes.toRequestBody("image/*".toMediaTypeOrNull())
                )

                val clientId = "Client-ID 06db70966b90761"
                ImgurClient.instance.uploadImage(clientId, requestFile)
                    .enqueue(object : Callback<ImgurResponse> {
                        override fun onResponse(call: Call<ImgurResponse>, response: Response<ImgurResponse>) {
                            if (response.isSuccessful && response.body() != null) {
                                val imgurLink = response.body()!!.data.link
                                saveToFirebase(newUsername, info, nomorTelepon, email, imgurLink)
                            } else {
                                Toast.makeText(requireContext(), "Gagal upload ke Imgur", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                            Toast.makeText(requireContext(), "Upload error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        } else {
            val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
            val prevUri = sharedPref.getString("profileImageUri", null)
            saveToFirebase(newUsername, info, nomorTelepon, email, prevUri)
        }
    }

    private fun saveToFirebase(username: String, info: String, nomorTelepon: String, email: String, imageUrl: String?) {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val oldUsername = sharedPref.getString("username", null) ?: username

        with(sharedPref.edit()) {
            putString("username", username)
            putString("info", info)
            putString("nomorTelepon", nomorTelepon)
            putString("email", email)
            if (imageUrl != null) putString("profileImageUri", imageUrl)
            apply()
        }

        database.child(oldUsername).get().addOnSuccessListener { snapshot ->
            val password = snapshot.child("password").value?.toString() ?: ""
            val confirmPassword = snapshot.child("confirmpassword").value?.toString() ?: ""

            val updatedUser = mapOf(
                "username" to username,
                "email" to email,
                "info" to info,
                "nomorTelepon" to nomorTelepon,
                "password" to password,
                "confirmpassword" to confirmPassword,
                "profileImageUri" to imageUrl
            )

            if (oldUsername != username) database.child(oldUsername).removeValue()

            database.child(username).setValue(updatedUser)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Berhasil simpan ke Firebase", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal simpan ke Firebase", Toast.LENGTH_SHORT).show()
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

                val imageUriString = snapshot.child("profileImageUri").value?.toString()
                if (!imageUriString.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(imageUriString)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivProfile)
                }
            } else {
                Toast.makeText(requireContext(), "Data tidak ditemukan di Firebase", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal ambil data dari Firebase", Toast.LENGTH_SHORT).show()
        }
    }
}
