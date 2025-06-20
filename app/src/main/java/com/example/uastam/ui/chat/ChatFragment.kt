package com.example.uastam.ui.chat

import android.os.Bundle
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

class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatList: List<ChatItem>
    private lateinit var adapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.btnclose)?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutChat)
        recyclerView = view.findViewById(R.id.recyclerViewChat)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Contoh data chatList
        chatList = listOf(
            ChatItem("onyourmark", "", R.drawable.profilepict),
            ChatItem("john_doe", "", R.drawable.profilepict),
            ChatItem("alice", "", R.drawable.profilepict)
        )

        // Adapter dengan klik listener untuk buka ChatDetailFragment
        adapter = ChatListAdapter(chatList) { chat ->
            // Debug log
            android.util.Log.d("ChatFragment", "Clicked chat: ${chat.nama}")

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

        // Setup tabs
        listOf("Semua", "Pembelian", "Menjual").forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                filterByTab(tab.text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(0)?.select()
    }

    private fun filterByTab(tab: String) {
        // Dummy filter: tetap tampil semua chat
        adapter.updateData(chatList)
    }
}
