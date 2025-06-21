package com.example.uastam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uastam.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // ✅ Cek apakah user sudah login
        val sharedPref = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        // Delay 2 detik sebelum pindah activity
        window.decorView.postDelayed({
            if (isLoggedIn) {
                // Jika sudah login langsung ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Jika belum login arahkan ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish() // supaya tidak bisa kembali ke splash
        }, 2000)
    }
}
