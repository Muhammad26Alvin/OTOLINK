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
import com.example.uastam.ui.kategori.KategoriMotorFragment

class KategoriMotorFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    private lateinit var adapter: AdapterClassMotor
    private lateinit var fullList: List<DataClassMotor>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kategori_motor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: ImageView = view.findViewById(R.id.btnbackmotor)
        val searchBar: EditText = view.findViewById(R.id.searchBar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewkategorimotor)

        btnBack.setOnClickListener { requireActivity().onBackPressed() }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fullList = listOf(
            DataClassMotor(
                R.drawable.sampelmobil1,
                "Rp 15.000.000",
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
            DataClassMotor(
                R.drawable.sampel3,
                "Rp 15.000.000",
                "Vario 160 Second",
                "2020",
                "Sukarame, Bandar Lampung",
                "Skuter Matic",
                "Honda Vario 160",
                "7.000",
                "Hitam",
                "CVT",
                "STNK & BPKB Lengkap",
                "Sukarame, Bandar Lampung",
                "Muhammad Alvin"
            ),
            DataClassMotor(
                R.drawable.sampel3,
                "Rp 15.000.000",
                "Vario 160 Second",
                "2020",
                "Sukarame, Bandar Lampung",
                "Skuter Matic",
                "Honda Vario 160",
                "7.000",
                "Hitam",
                "CVT",
                "STNK & BPKB Lengkap",
                "Sukarame, Bandar Lampung",
                "Muhammad Alvin"
            ),
            DataClassMotor(
                R.drawable.sampel3,
                "Rp 15.000.000",
                "Vario 160 Second",
                "2020",
                "Sukarame, Bandar Lampung",
                "Skuter Matic",
                "Honda Vario 160",
                "7.000",
                "Hitam",
                "CVT",
                "STNK & BPKB Lengkap",
                "Sukarame, Bandar Lampung",
                "Muhammad Alvin"
            )

        )


        adapter = AdapterClassMotor(fullList.toMutableList()) { selectedMotor ->
            val detailFragment = DetailMotorFragment.newInstance(selectedMotor)
            parentFragmentManager.beginTransaction()
                .replace(R.id.motorcontainer, detailFragment)
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
