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

class EditProfileFragment : Fragment() {

    private lateinit var ivProfile: ImageView
    private lateinit var etNama: EditText
    private lateinit var etInfo: EditText
    private lateinit var etNomorTelepon: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSimpan: TextView

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

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        ivProfile = view.findViewById(R.id.imgProfile) // pastikan ada ImageView dengan id ini di layout
        etNama = view.findViewById(R.id.edittextnama)
        etInfo = view.findViewById(R.id.edittextinfo)
        etNomorTelepon = view.findViewById(R.id.editnomortelepon)
        etEmail = view.findViewById(R.id.textEmail)
        btnSimpan = view.findViewById(R.id.btnsimpan)

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
        val nama = etNama.text.toString().trim()
        val info = etInfo.text.toString().trim()
        val nomorTelepon = etNomorTelepon.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (nama.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Nama dan Email harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nama", nama)
            putString("info", info)
            putString("nomorTelepon", nomorTelepon)
            putString("email", email)
            if (imageUri != null) {
                putString("profileImageUri", imageUri.toString())
            }
            apply()
        }

        val resultBundle = Bundle()
        resultBundle.putString("newName", nama)
        parentFragmentManager.setFragmentResult("requestKeyName", resultBundle)

        Toast.makeText(requireContext(), "Data tersimpan", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    private fun loadProfile() {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        etNama.setText(sharedPref.getString("nama", ""))
        etInfo.setText(sharedPref.getString("info", ""))
        etNomorTelepon.setText(sharedPref.getString("nomorTelepon", ""))
        etEmail.setText(sharedPref.getString("email", ""))

        val imageUriString = sharedPref.getString("profileImageUri", null)
        if (!imageUriString.isNullOrEmpty()) {
            imageUri = Uri.parse(imageUriString)
            ivProfile.setImageURI(imageUri)
        }
    }
}
