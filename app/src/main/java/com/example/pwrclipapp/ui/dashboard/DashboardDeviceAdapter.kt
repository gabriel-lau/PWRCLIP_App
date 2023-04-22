package com.example.pwrclipapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pwrclipapp.R
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.Format
import java.text.SimpleDateFormat

class DashboardDeviceAdapter(private val dataSet: List<DashboardDeviceViewModel>) :  RecyclerView.Adapter<DashboardDeviceAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textViewDeviceName: TextView
        val graphViewDeviceUsage: GraphView
        init {
            textViewDeviceName = view.findViewById(R.id.textView_DeviceName)
            graphViewDeviceUsage = view.findViewById(R.id.graphView_DeviceUsage)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DashboardDeviceAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_dashboard_device, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: DashboardDeviceAdapter.ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewDeviceName.text = dataSet[position].name
        val lineGraphSeries = LineGraphSeries<DataPoint>(dataSet[position].dataPoints)
        viewHolder.graphViewDeviceUsage.addSeries(lineGraphSeries)
        viewHolder.graphViewDeviceUsage.gridLabelRenderer.setLabelFormatter(object: DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                if (isValueX) {
                    return value.toInt().toString()
                }
                return String.format("%.2f",value) + "kWh"//formatLabel(value, isValueX)
            }
        })
        viewHolder.graphViewDeviceUsage.viewport.setMinX(1.0)
        viewHolder.graphViewDeviceUsage.viewport.setMaxX(31.0)
        viewHolder.graphViewDeviceUsage.gridLabelRenderer.setHumanRounding(false)

    }
}