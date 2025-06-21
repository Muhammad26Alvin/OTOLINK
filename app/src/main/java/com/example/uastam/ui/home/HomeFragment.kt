package com.example.uastam.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.uastam.R
import com.example.uastam.ui.sampel.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lokasiText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageSlider = view.findViewById<ImageSlider>(R.id.ImageSlider)
        val slideModels = ArrayList<SlideModel>()

        slideModels.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        imageSlider.setImageList(slideModels)

        lokasiText = view.findViewById(R.id.namalokasi)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLocation()

        val id1 = view.findViewById<View>(R.id.Item1)
        id1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, Sampel1Fragment())
                .addToBackStack(null)
                .commit()
        }

        val id2 = view.findViewById<View>(R.id.Item2)
        id2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, Sampel2Fragment())
                .addToBackStack(null)
                .commit()
        }

        val id3 = view.findViewById<View>(R.id.Item3)
        id3.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, Sampel3Fragment())
                .addToBackStack(null)
                .commit()
        }

        val id4 = view.findViewById<View>(R.id.Item4)
        id4.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, Sampel4Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val city = addresses[0].locality ?: "Tidak diketahui"
                        lokasiText.text = city
                    } else {
                        lokasiText.text = "Lokasi tidak ditemukan"
                    }
                } else {
                    lokasiText.text = "Tidak bisa mendapatkan lokasi"
                }
            }.addOnFailureListener {
                lokasiText.text = "Gagal mendapatkan lokasi"
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View?>(R.id.fab_sell)
        fabSell?.let {
            it.isEnabled = true
            it.alpha = 1f
        }
    }
}
