package com.example.uastam.ui.itemchat
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ChatPrefs {
    private const val PREFS_NAME = "chat_prefs"

    fun getMessages(context: Context, chatName: String): List<Message> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString("chat_$chatName", "[]")
        return Gson().fromJson(json, object : TypeToken<List<Message>>() {}.type)
    }

    fun saveMessages(context: Context, chatName: String, messages: List<Message>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(messages)
        prefs.edit().putString("chat_$chatName", json).apply()
    }
}


data class ChatItem(
    val nama: String = "",
    val lastMessage: String = "",
    val profileImageRes: Int = 0
)

data class Message(
    val sender: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

