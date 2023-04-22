package com.example.pwrclipapp.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pwrclipapp.MainActivity
import com.example.pwrclipapp.R
import com.example.pwrclipapp.databinding.FragmentAddBinding
import com.example.pwrclipapp.databinding.FragmentAddWIFIBinding
import com.example.pwrclipapp.databinding.FragmentDeviceDialogListDialogBinding
import com.example.pwrclipapp.ui.home.AppliancesViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

class AddWIFIFragment : Fragment() {
    private var _binding: FragmentAddWIFIBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddWIFIBinding.inflate(inflater, container, false)

        val textFieldName = binding.textFieldName
        val textFieldID = binding.textFieldDeviceID

        val buttonAdd = binding.buttonAdd

        val buttonTV = binding.buttonTV
        val buttonPC = binding.buttonPC
        val buttonFan = binding.buttonFan
        val buttonAC = binding.buttonAC
        val buttonFridge = binding.buttonFridge
        val buttonMicrowave = binding.buttonMicrowave
        val buttonOven = binding.buttonOven
        val buttonLight = binding.buttonLight

        var selectedImage: Int = R.drawable.tv_48px
        buttonTV.setOnClickListener {
            deselectAll()
            buttonTV.isEnabled = false
            selectedImage = R.drawable.tv_48px
        }
        buttonPC.setOnClickListener {
            deselectAll()
            buttonPC.isEnabled = false
            selectedImage = R.drawable.computer_48px
        }
        buttonFan.setOnClickListener {
            deselectAll()
            buttonFan.isEnabled = false
            selectedImage = R.drawable.mode_fan_48px
        }
        buttonAC.setOnClickListener {
            deselectAll()
            buttonAC.isEnabled = false
            selectedImage = R.drawable.ac_unit_48px
        }
        buttonFridge.setOnClickListener {
            deselectAll()
            buttonFridge.isEnabled = false
            selectedImage = R.drawable.fridge_48px
        }
        buttonMicrowave.setOnClickListener {
            deselectAll()
            buttonMicrowave.isEnabled = false
            selectedImage = R.drawable.microwave_48px
        }
        buttonOven.setOnClickListener {
            deselectAll()
            buttonOven.isEnabled = false
            selectedImage = R.drawable.oven_48px
        }
        buttonLight.setOnClickListener {
            deselectAll()
            buttonLight.isEnabled = false
            selectedImage = R.drawable.light_48px
        }

        buttonAdd.setOnClickListener {
            (activity as MainActivity).enableMQTTSubscribe()
            val formatter = SimpleDateFormat("HH:mm")
            val date = Date()
            val current = formatter.format(date)
            (activity as MainActivity).data.add(AppliancesViewModel(
                selectedImage,
                textFieldName.text.toString(),
                "Turned on at $current",
                "0kWh"))
            Snackbar.make(binding.root, "New device added", Snackbar.LENGTH_SHORT).show()
            val navController = findNavController()
            navController.navigate(R.id.navigation_home)
        }
        return binding.root
    }
    private fun deselectAll(){
        binding.buttonTV.isEnabled = true
        binding.buttonPC.isEnabled = true
        binding.buttonFan.isEnabled = true
        binding.buttonAC.isEnabled = true
        binding.buttonFridge.isEnabled = true
        binding.buttonMicrowave.isEnabled = true
        binding.buttonOven.isEnabled = true
        binding.buttonLight.isEnabled = true

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}