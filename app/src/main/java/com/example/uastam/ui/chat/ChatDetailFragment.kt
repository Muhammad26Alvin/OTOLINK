package com.example.uastam.ui.itemchat

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

class ChatDetailFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var btnSend: ImageView
    private lateinit var btnClose: ImageView
    private lateinit var adapter: chatadapter

    private var chatName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sembunyikan bottom bar
        (activity as? MainActivity)?.showBottomBar(false)

        // Ambil nama chat dari arguments
        chatName = arguments?.getString("NAMA_CHAT")
        Log.d("ChatDetailFragment", "Received chat name: $chatName")

        val namaChatTextView = view.findViewById<TextView>(R.id.namaChat)
        namaChatTextView.text = chatName

        if (chatName == null) {
            // Jika tidak ada nama, kembali ke fragment sebelumnya
            requireActivity().onBackPressed()
            return
        }

        // Inisialisasi view
        recyclerView = view.findViewById(R.id.recyclerViewMessages)
        editTextMessage = view.findViewById(R.id.editTextMessage)
        btnSend = view.findViewById(R.id.btnSend)
        btnClose = view.findViewById(R.id.btnclose)

        // Ambil pesan tersimpan sesuai nama chat
        val savedMessages = ChatPrefs.getMessages(requireContext(), chatName!!)
        adapter = chatadapter(savedMessages.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.itemCount - 1)

        // Tombol kirim pesan
        btnSend.setOnClickListener {
            val text = editTextMessage.text.toString()
            if (text.isNotBlank()) {
                val message = Message(text, true)
                adapter.addMessage(message)
                recyclerView.scrollToPosition(adapter.itemCount - 1)
                editTextMessage.text.clear()

                // Simpan pesan setelah dikirim
                ChatPrefs.saveMessages(requireContext(), chatName!!, adapter.getMessages())
            }
        }

        // Tombol keluar chat detail
        btnClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Tampilkan kembali bottom bar saat keluar
        (activity as? MainActivity)?.showBottomBar(true)

        // Simpan pesan saat fragment dihancurkan
        chatName?.let {
            ChatPrefs.saveMessages(requireContext(), it, adapter.getMessages())
        }
    }
}
