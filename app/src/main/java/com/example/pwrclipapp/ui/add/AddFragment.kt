package com.example.pwrclipapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pwrclipapp.MainActivity
import com.example.pwrclipapp.R
import com.example.pwrclipapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cardViewBluetooth = binding.cardViewBluetooth
        /*
        cardViewBluetooth.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                activity,
                "clicked",
                Toast.LENGTH_SHORT
            ).show()
        })*/
        val cardViewWIFI = binding.cardViewWiFi
        cardViewWIFI.setOnClickListener(View.OnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.navigation_add_wifi)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}