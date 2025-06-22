package com.example.uastam.ui.kategori

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.google.firebase.database.*

class KategoriMobilFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    private lateinit var adapter: AdapterClassMobil
    private var fullList: MutableList<DataClassMobil> = mutableListOf()
    private lateinit var dbRef: DatabaseReference

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
        adapter = AdapterClassMobil(fullList) { selectedMobil ->
            val detailFragment = DetailMobilFragment.newInstance(selectedMobil)
            parentFragmentManager.beginTransaction()
                .replace(R.id.mobilcontainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference("jualan/mobil")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fullList.clear()
                for (child in snapshot.children) {
                    val data = child.getValue(DataClassMobil::class.java)
                    data?.let { fullList.add(it) }
                }
                adapter.updateData(fullList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }
        })
    }

    private fun filterList(query: String) {
        val filtered = fullList.filter {
            it.deskripsi.contains(query, ignoreCase = true) ||
                    it.harga.contains(query, ignoreCase = true) ||
                    it.alamat.contains(query, ignoreCase = true)
        }
        adapter.updateData(filtered)
    }
}
