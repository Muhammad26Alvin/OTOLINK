package com.example.uastam.ui.jual

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.uastam.MainActivity
import com.example.uastam.R

class FormInputFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    private lateinit var imagePreview: ImageView
    private lateinit var tipe: EditText
    private lateinit var merk: EditText
    private lateinit var tahun: EditText
    private lateinit var jarak: EditText
    private lateinit var warna: EditText
    private lateinit var transmisi: EditText
    private lateinit var sertifikasi: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_form_input, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? MainActivity)?.showBottomBar(false)

        imagePreview = view.findViewById(R.id.upload_image_preview)
        tipe = view.findViewById(R.id.tipebarang)
        merk = view.findViewById(R.id.merkbarang)
        tahun = view.findViewById(R.id.tahunbarang)
        jarak = view.findViewById(R.id.jaraktempuhbarang)
        warna = view.findViewById(R.id.warnabarang)
        transmisi = view.findViewById(R.id.transmisibarang)
        sertifikasi = view.findViewById(R.id.sertifikasibarang)

        view.findViewById<ImageView>(R.id.close).setOnClickListener {
            requireActivity().onBackPressed()
        }

        view.findViewById<View>(R.id.uploadfotoform1).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        view.findViewById<Button>(R.id.btnform1).setOnClickListener {
            if (tipe.text.isBlank() || merk.text.isBlank() || imageUri == null) {
                Toast.makeText(requireContext(), "Lengkapi semua data dan pilih gambar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bundle = Bundle().apply {
                putString("tipe", tipe.text.toString())
                putString("merk", merk.text.toString())
                putString("tahun", tahun.text.toString())
                putString("jarak", jarak.text.toString())
                putString("warna", warna.text.toString())
                putString("transmisi", transmisi.text.toString())
                putString("sertifikasi", sertifikasi.text.toString())
                putString("imageUri", imageUri.toString())
                putString("kategori", "mobil")
            }

            val formInput2 = FormInput2Fragment()
            formInput2.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.framelayout, formInput2)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imagePreview.setImageURI(imageUri)
            imagePreview.visibility = View.VISIBLE
            view?.findViewById<ImageView>(R.id.upload_icon)?.visibility = View.GONE
            view?.findViewById<TextView>(R.id.upload_text)?.visibility = View.GONE
        }
    }
}
