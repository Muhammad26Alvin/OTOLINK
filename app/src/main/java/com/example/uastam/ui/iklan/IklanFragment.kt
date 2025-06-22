package com.example.uastam.ui.iklan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.ui.kategori.AdapterClassMobil
import com.example.uastam.ui.kategori.DataClassMobil
import com.example.uastam.ui.kategori.DetailMobilFragment
import com.google.firebase.database.*

class IklanFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterClassMobil
    private val favoriteList = mutableListOf<DataClassMobil>()

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_iklan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerfavourite)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterClassMobil(favoriteList) { selectedItem ->
            val detailFragment = DetailMobilFragment.newInstance(selectedItem)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = adapter

        loadFavoriteData()
    }

    private fun loadFavoriteData() {
        val sharedPref = requireActivity().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null) ?: return
        val favoritRef = FirebaseDatabase.getInstance().getReference("favorit/$username")

        favoritRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoriteList.clear()
                for (itemSnapshot in snapshot.children) {
                    val data = itemSnapshot.getValue(DataClassMobil::class.java)
                    data?.let {
                        it.isFavorite = true // wajib tandai sebagai favorit
                        favoriteList.add(it)
                    }
                }
                adapter.updateData(favoriteList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal memuat favorit", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
