package com.example.uastam.ui.jual

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uastam.MainActivity
import com.example.uastam.R
import com.example.uastam.ui.account.EditProfileFragment

class FormInputFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_input, container, false)
    }

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imagePreview: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.showBottomBar(false)

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        imagePreview = view.findViewById(R.id.upload_image_preview)
        val uploadFotoLayout: View = view.findViewById(R.id.uploadfotoform1)
        uploadFotoLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val lanjut: Button = view.findViewById(R.id.btnform1)
        lanjut.setOnClickListener {
            val forminput2 = FormInput2Fragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.framelayout, forminput2)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                imagePreview.setImageURI(it)
                imagePreview.visibility = View.VISIBLE
                view?.findViewById<ImageView>(R.id.upload_icon)?.visibility = View.GONE
                view?.findViewById<TextView>(R.id.upload_text)?.visibility = View.GONE
            }
        }
    }
}