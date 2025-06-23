package com.example.uastam.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.adapter.NotifikasiAdapter
import com.example.uastam.ui.home.Notifications

class NotificationsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotifikasiAdapter
    private val dataNotifikasi = listOf(
        Notifications("Alvin", "Barangmu sudah diposting", "08.15", R.drawable.profilepict),
        Notifications("Admin", "Akun kamu telah diverifikasi", "09.00", R.drawable.profilepict),
        Notifications("Sistem", "Password berhasil diubah", "10.20", R.drawable.profilepict)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        view.findViewById<ImageView>(R.id.close).setOnClickListener {
            requireActivity().onBackPressed()
        }

        recyclerView = view.findViewById(R.id.RecyclerViewNotifications)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NotifikasiAdapter(dataNotifikasi)
        recyclerView.adapter = adapter
        return view
    }
}
