package com.example.pwrclipapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pwrclipapp.databinding.FragmentDeviceBinding
import com.google.android.material.snackbar.Snackbar

class DeviceFragment : Fragment() {
    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!
    //private var device: Device = device
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        val bundle = arguments
        var pos = bundle!!.getInt("edttext")


        val root: View = binding.root
        val snackbar = Snackbar
            .make(root, pos.toString(), Snackbar.LENGTH_LONG)
        snackbar.show()
        return root
    }
}