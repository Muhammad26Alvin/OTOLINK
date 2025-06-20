package com.example.uastam.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.ui.itempesanan.Pesanan
import com.example.uastam.ui.itempesanan.PesananAdapter
import com.google.android.material.tabs.TabLayout

class PesananFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PesananAdapter

    // Data dummy (bisa diganti dari API)
    private val allPesanan = listOf(
        Pesanan("Mahindra BE 6e", "Rajabasa, Bandar Lampung", "Rp 250.000.000,00", R.drawable.sampel1, "semua"),
        Pesanan("Toyota Avanza", "Bandar Lampung", "Rp 150.000.000,00", R.drawable.sampel1, "menunggu pembayaran"),
        Pesanan("Honda Jazz", "Lampung Selatan", "Rp 200.000.000,00", R.drawable.sampel1, "menunggu konfirmasi"),
        Pesanan("Suzuki Ertiga", "Bandar Lampung", "Rp 180.000.000,00", R.drawable.sampel1, "transaksi berlangsung"),
        Pesanan("Daihatsu Ayla", "Lampung Tengah", "Rp 120.000.000,00", R.drawable.sampel1, "dibatalkan"),
    )

    private var filteredPesanan = allPesanan.toList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pesanan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: ImageView = view.findViewById(R.id.close)
        btnBack.setOnClickListener { requireActivity().onBackPressed() }

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        recyclerView = view.findViewById(R.id.recyclerViewPesanan)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PesananAdapter(filteredPesanan) { pesanan ->
            Toast.makeText(context, "Dipilih: ${pesanan.nama}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Setup tabs
        val tabs = listOf("Semua", "Menunggu Pembayaran", "Menunggu Konfirmasi", "Transaksi Berlangsung", "Dibatalkan", "Selesai")
        tabs.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                filterPesananByStatus(tab.text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Pilih tab "Semua" sebagai default
        tabLayout.getTabAt(0)?.select()
    }

    private fun filterPesananByStatus(status: String) {
        filteredPesanan = if (status.equals("Semua", ignoreCase = true)) {
            allPesanan
        } else {
            allPesanan.filter { it.status.equals(status, ignoreCase = true) }
        }
        adapter.updateData(filteredPesanan)
    }
}
