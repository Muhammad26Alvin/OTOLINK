package com.example.uastam.ui.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.R
import com.example.uastam.ui.itemchat.ChatDetailFragment
import com.example.uastam.ui.itemchat.ChatItem
import com.example.uastam.ui.itemchat.ChatListAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*

class ChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatListAdapter
    private val chatList = mutableListOf<ChatItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol kembali
        view.findViewById<ImageView>(R.id.btnclose)?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutChat)
        recyclerView = view.findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi adapter dengan klik listener
        adapter = ChatListAdapter(chatList) { chat ->
            val chatDetailFragment = ChatDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("NAMA_CHAT", chat.nama)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.framelayout, chatDetailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        // Tambahkan tab
        listOf("Semua", "Pembelian", "Menjual").forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        // Listener tab
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                filterByTab(tab.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Default pilih tab pertama
        tabLayout.getTabAt(0)?.select()

        // Ambil data chat
        fetchChatList()
    }

    private fun fetchChatList() {
        val currentUsername = getLoggedInUsername()
        Log.d("ChatFragment", "Username login: $currentUsername")

        if (currentUsername.isEmpty()) {
            Log.e("ChatFragment", "Username kosong, tidak bisa ambil chat list")
            return
        }

        val dbRef = FirebaseDatabase.getInstance()
            .getReference("chats")
            .child(currentUsername)

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (child in snapshot.children) {
                    val receiver = child.key ?: continue
                    chatList.add(ChatItem(nama = receiver))
                }
                adapter.updateData(chatList)
                Log.d("ChatFragment", "Berhasil ambil daftar chat, total: ${chatList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatFragment", "Gagal ambil data chat: ${error.message}")
            }
        })
    }

    private fun filterByTab(tab: String) {
        // Untuk sekarang hanya tampilkan semua (belum ada kategori)
        adapter.updateData(chatList)
    }

    private fun getLoggedInUsername(): String {
        val prefs = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        return prefs.getString("username", "") ?: ""
    }
}
