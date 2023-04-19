package com.example.pwrclipapp.ui.home

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pwrclipapp.R
import com.example.pwrclipapp.databinding.FragmentDeviceDialogListDialogBinding

class DeviceDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDeviceDialogListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceDialogListDialogBinding.inflate(inflater, container, false)
        val bundle = arguments
        var pos = bundle!!.getInt("edttext")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}