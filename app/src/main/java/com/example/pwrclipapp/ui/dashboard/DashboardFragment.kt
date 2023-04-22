package com.example.pwrclipapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pwrclipapp.MainActivity
import com.example.pwrclipapp.databinding.FragmentDashboardBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.jjoe64.graphview.series.DataPoint
import java.util.Date
import kotlin.random.Random


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var devicesList = ArrayList<DashboardDeviceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayOf("Last 24 hours", "Last week", "Last month", "Last year")
        val autoCompleteTextViewFilter:MaterialAutoCompleteTextView = binding.autoCompleteTextViewFilter as MaterialAutoCompleteTextView
        autoCompleteTextViewFilter.setSimpleItems(items)
        autoCompleteTextViewFilter.setOnItemClickListener { parent, view, position, id ->
            run {
                populateRecyclerView(position)
            }
        }
    }
    fun generateDataPointList(xPoints: Int): Array<DataPoint> {
        var min = 0.5 // per day
        var max = 5.0
        var date = Date()
        if (xPoints == 24) { // per hour
            min = 0.01
            max = 0.6
        } else if(xPoints == 12) {
            min = 5.0
            max = 10.0
        }
        val dataPointList = Array<DataPoint>(xPoints) {DataPoint(0.0,0.0)}
        for (i: Int in 0..xPoints-1) {
            //var pastDate = DateTime()
            dataPointList.set(i,DataPoint(i+1.toDouble(), Random.nextDouble(min, max)))
        }
        return dataPointList
    }
    fun generateOverallDataPointList(xPoints: Int): Array<DataPoint> {
        val dataPointList = Array<DataPoint>(xPoints) {DataPoint(0.0,0.0)}
        for (i: Int in 0..xPoints-1) {
            var value: Double = 0.0
            for (device in devicesList) {
                value += device.dataPoints[i].y
            }
            dataPointList.set(i,DataPoint(i+1.toDouble(), value))
        }
        return dataPointList
    }
    fun populateRecyclerView(position: Int) { // 0 for "Last 24 hours", 1 for "Last week", 2 for "Last month", 3 for "Last year"
        var xPoints: Int = 0
        when(position) {
            0 -> xPoints = 24
            1 -> xPoints = 7
            2 -> xPoints = 31
            3 -> xPoints = 12
        }
        devicesList.clear()
        for(i in (activity as MainActivity).data) {
            devicesList.add(DashboardDeviceViewModel(i.name, generateDataPointList(xPoints)))
        }
        devicesList.add(0, DashboardDeviceViewModel("Overall", generateOverallDataPointList(xPoints)))

        val recyclerView: RecyclerView = binding.recyclerViewDashboard
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // This will pass the ArrayList to our Adapter
        val adapter = DashboardDeviceAdapter(devicesList)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}