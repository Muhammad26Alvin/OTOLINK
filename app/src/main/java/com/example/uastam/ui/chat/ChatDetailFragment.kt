package com.example.uastam.ui.itemchat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uastam.MainActivity
import com.example.uastam.R
import com.google.firebase.database.*

class ChatDetailFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var btnSend: ImageView
    private lateinit var btnClose: ImageView
    private lateinit var adapter: chatadapter

    private var chatName: String? = null
    private var currentUser: String? = null
    private lateinit var chatRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_chat_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.showBottomBar(false)

        val sharedPref = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        currentUser = sharedPref.getString("username", null)
        chatName = arguments?.getString("NAMA_CHAT")

        Log.d("ChatDetailFragment", "CurrentUser: $currentUser | ChatWith: $chatName")

        val namaChatTextView = view.findViewById<TextView>(R.id.namaChat)
        namaChatTextView.text = chatName

        if (chatName.isNullOrEmpty() || currentUser.isNullOrEmpty()) {
            Log.e("ChatDetailFragment", "currentUser atau chatName kosong, kembali ke halaman sebelumnya.")
            requireActivity().supportFragmentManager.popBackStack()
            return
        }

        recyclerView = view.findViewById(R.id.recyclerViewMessages)
        editTextMessage = view.findViewById(R.id.editTextMessage)
        btnSend = view.findViewById(R.id.btnSend)
        btnClose = view.findViewById(R.id.btnclose)

        adapter = chatadapter(mutableListOf(), currentUser!!)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        chatRef = FirebaseDatabase.getInstance().getReference("chats")
            .child(currentUser!!)
            .child(chatName!!)

        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (child in snapshot.children) {
                    val message = child.getValue(Message::class.java)
                    if (message != null) {
                        messages.add(message)
                    }
                }
                adapter.setMessages(messages)
                recyclerView.scrollToPosition(messages.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatDetail", "Gagal ambil chat: ${error.message}")
            }
        })

        btnSend.setOnClickListener {
            val text = editTextMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                val message = Message(sender = currentUser!!, text = text)

                // Simpan ke user -> lawan
                val userRef = FirebaseDatabase.getInstance().getReference("chats")
                    .child(currentUser!!)
                    .child(chatName!!)
                userRef.push().setValue(message)
                    .addOnSuccessListener {
                        Log.d("ChatSend", "Pesan berhasil disimpan di /chats/$currentUser/$chatName")
                    }
                    .addOnFailureListener {
                        Log.e("ChatSend", "Gagal simpan pesan: ${it.message}")
                    }

                // Simpan ke lawan -> user
                val opponentRef = FirebaseDatabase.getInstance().getReference("chats")
                    .child(chatName!!)
                    .child(currentUser!!)
                opponentRef.push().setValue(message)
                    .addOnSuccessListener {
                        Log.d("ChatSend", "Pesan juga disimpan di /chats/$chatName/$currentUser")
                    }
                    .addOnFailureListener {
                        Log.e("ChatSend", "Gagal simpan ke lawan: ${it.message}")
                    }

                editTextMessage.text.clear()
            }
        }

        btnClose.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showBottomBar(true)
    }
}
