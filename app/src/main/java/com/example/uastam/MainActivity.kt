package com.example.uastam

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uastam.databinding.ActivityMainBinding
import com.example.uastam.ui.account.AccountFragment
import com.example.uastam.ui.chat.ChatFragment
import com.example.uastam.ui.home.HomeFragment
import com.example.uastam.ui.iklan.IklanFragment
import com.example.uastam.ui.jual.JualFragment
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    fun showBottomBar(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        binding.bottombar.visibility = visibility
        binding.navigationview.visibility = visibility
        binding.fabSell.visibility = visibility
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.navigationview.background = null

        // Handle klik item bottom navigation
        binding.navigationview.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_chat -> replaceFragment(ChatFragment())
                R.id.navigation_fav -> replaceFragment(IklanFragment())
                R.id.navigation_account -> replaceFragment(AccountFragment())
            }
            true
        }

        binding.fabSell.setOnClickListener {
            replaceFragment(JualFragment())

            // Atau tampilkan pesan
//             Toast.makeText(this, "FAB ditekan!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, fragment)
            .commit()
    }
}
