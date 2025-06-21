package com.example.uastam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uastam.ui.login.HomePageFragment
import com.example.uastam.ui.login.RegisterFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Cek apakah sudah login
        val sharedPref = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // ✅ Jika sudah login langsung ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // tutup LoginActivity agar tidak bisa kembali
            return
        }

        // ✅ Belum login, tampilkan halaman login biasa
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.firstcontainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonsignup: Button = findViewById(R.id.btnsignup)
        buttonsignup.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.firstcontainer, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        val buttonlogin: Button = findViewById(R.id.btnlogin)
        buttonlogin.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.firstcontainer, HomePageFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
