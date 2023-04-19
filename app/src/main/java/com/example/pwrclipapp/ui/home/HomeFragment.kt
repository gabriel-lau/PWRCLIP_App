package com.example.pwrclipapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pwrclipapp.R
import com.example.pwrclipapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


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

        // ArrayList of class ItemsViewModel
        val data = ArrayList<AppliancesViewModel>()

        data.add(AppliancesViewModel(R.drawable.microwave_48px, "Microwave", "Turned on at 12:00", "50W"))
        data.add(AppliancesViewModel(R.drawable.oven_48px, "Oven", "Turned on at 09:00", "100W"))
        data.add(AppliancesViewModel(R.drawable.fridge_48px, "Fridge", "Turned on at 3:00", "100W"))

        // This will pass the ArrayList to our Adapter
        val adapter = AppliancesAdapter(data, object: AppliancesAdapter.OnNoteListener{
            override fun onNoteClick(position: Int) {
                val navController = findNavController()
                val bundle = Bundle()
                bundle.putInt("edttext", position)
                // navController.navigate(R.id.navigation_device, bundle)
                val deviceDialogFragment = DeviceDialogFragment()
                deviceDialogFragment.arguments = bundle
                deviceDialogFragment.show(childFragmentManager, DeviceDialogFragment.TAG)
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