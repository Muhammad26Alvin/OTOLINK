package com.example.uastam.ui.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.ui.kategori.KategoriMobilFragment

class KategoriMobilFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    private lateinit var adapter: AdapterClassMobil
    private lateinit var fullList: List<DataClassMobil>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kategori_mobil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: ImageView = view.findViewById(R.id.btnbackmobil)
        val searchBar: EditText = view.findViewById(R.id.searchBar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewkategorimobil)

        btnBack.setOnClickListener { requireActivity().onBackPressed() }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fullList = listOf(
            DataClassMobil(
                R.drawable.sampelmobil1,
                "Rp 1.350.000.000",
                "KOENIGSEGG AGERA RS",
                "2022",
                "Rajabasa, Bandar Lampung",
                "KOENIGSEGG AGERA RS",
                "MOBIL SPORT",
                "7.000",
                "BLACK DOVE",
                "AUTOMATIC 7-SPEED DUAL CLUTCH",
                "STNK & BPKB LENGKAP",
                "RAJABASA, BANDAR LAMPUNG",
                "MUHAMMAD ALVIN"
            ),
            DataClassMobil(
                R.drawable.sampelmobil2,
                "Rp 530.000.000",
                "HONDA CIVIC 1.5L TURBO",
                "2020",
                "TEGINENENG, PESAWARAN",
                "HONDA CIVIC 1.5L TURBO",
                "MOBIL",
                "800",
                "BLUE METALIC",
                "CVT",
                "STNK & BPKB Lengkap",
                "TEGINENENG, PESAWARAN",
                "ANINDITA TRI MULIA"
            ),
            DataClassMobil(
                R.drawable.sampelmobil3,
                "Rp 700.000.000",
                "TOYOTA FORTUNER 4X4",
                "2021",
                "RAJABASA, BANDAR LAMPUNG",
                "TOYOTA FORTUNER 4X4",
                "MOBIL",
                "700",
                "WHITE",
                "CVT",
                "STNK & BPKB Lengkap",
                "RAJABASA, BANDAR LAMPUNG",
                "RAHAYU INDAH LESTARI"
            ),
            DataClassMobil(
                R.drawable.sampelmobil4,
                "Rp 600.000.000",
                "JEEP RUBICON",
                "2022",
                "KEDAMAIAN, BANDAR LAMPUNG",
                "TOYOTA FORTUNER 4X4",
                "MOBIL",
                "700",
                "GREEN",
                "CVT",
                "STNK & BPKB Lengkap",
                "KEDAMAIAN, BANDAR LAMPUNG",
                "INTAN NUR LAILA"
            )
        )


        adapter = AdapterClassMobil(fullList.toMutableList()) { selectedMobil ->
            val detailFragment = DetailMobilFragment.newInstance(selectedMobil)
            parentFragmentManager.beginTransaction()
                .replace(R.id.mobilcontainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        searchBar.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }
        })
    }

    private fun filterList(query: String) {
        val filtered = fullList.filter {
            it.deskripsi.contains(query, ignoreCase = true) ||
                    it.harga.contains(query, ignoreCase = true) ||
                    it.lokasi.contains(query, ignoreCase = true)
        }
        adapter.updateData(filtered)
    }
}
