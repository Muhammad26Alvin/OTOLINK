package com.example.uastam.ui.iklan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.uastam.R
import com.example.uastam.ui.sampel.Sampel1Fragment
import com.example.uastam.ui.sampel.Sampel2Fragment
import com.example.uastam.ui.sampel.Sampel3Fragment
import com.example.uastam.ui.sampel.Sampel4Fragment

class IklanFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        val fabSell = requireActivity().findViewById<View>(R.id.fab_sell)
        fabSell.isEnabled = true
        fabSell.alpha = 1f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_iklan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // List ID favorit icon
        val favoriteIcons = listOf(
            view.findViewById<ImageView>(R.id.icon_favorite1),
            view.findViewById<ImageView>(R.id.icon_favorite2),
            view.findViewById<ImageView>(R.id.icon_favorite3),
            view.findViewById<ImageView>(R.id.icon_favorite4)
        )

        // Status favorit tiap icon
        val favoriteStates = mutableListOf(false, false, false, false)

        // Set onClickListener untuk toggle icon
        favoriteIcons.forEachIndexed { index, icon ->
            icon.setOnClickListener {
                favoriteStates[index] = !favoriteStates[index]
                icon.setImageResource(
                    if (favoriteStates[index]) R.drawable.ic_love else R.drawable.ic_favorite
                )
            }
        }

        // Klik item untuk pindah ke Sampel1Fragment
        val item1 = view.findViewById<View>(R.id.favorite1)
        item1.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel1Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Klik item untuk pindah ke Sampel1Fragment
        val item2 = view.findViewById<View>(R.id.favorite2)
        item2.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel2Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Klik item untuk pindah ke Sampel1Fragment
        val item3 = view.findViewById<View>(R.id.favorite3)
        item3.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel3Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        // Klik item untuk pindah ke Sampel1Fragment
        val item4 = view.findViewById<View>(R.id.favorite4)
        item4.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Sampel4Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
