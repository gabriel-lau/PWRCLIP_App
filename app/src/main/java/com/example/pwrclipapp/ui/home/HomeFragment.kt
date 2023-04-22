package com.example.pwrclipapp.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pwrclipapp.MainActivity
import com.example.pwrclipapp.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerViewHome
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val localData = (activity as MainActivity).data
        // This will pass the ArrayList to our Adapter
        val adapter = AppliancesAdapter(localData, object: AppliancesAdapter.OnNoteListener{
            override fun onNoteClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt("position", position)
                val deviceDialogFragment = DeviceDialogFragment()
                deviceDialogFragment.arguments = bundle
                deviceDialogFragment.show(childFragmentManager, DeviceDialogFragment.TAG)
                // val navController = findNavController()
                // navController.navigate(R.id.navigation_device, bundle)
            }
        })

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}