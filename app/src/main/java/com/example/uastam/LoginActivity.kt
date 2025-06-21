package com.example.uastam

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.uastam.ui.login.HomePageFragment
import com.example.uastam.ui.login.RegisterFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                .replace(R.id.firstcontainer, RegisterFragment()) // ✅ Gunakan objek RegisterFragment()
                .addToBackStack(null)
                .commit()
        }

        val buttonlogin: Button = findViewById(R.id.btnlogin)
        buttonlogin.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.firstcontainer, HomePageFragment()) // ✅ Gunakan objek RegisterFragment()
                .addToBackStack(null)
                .commit()
        }
    }
}