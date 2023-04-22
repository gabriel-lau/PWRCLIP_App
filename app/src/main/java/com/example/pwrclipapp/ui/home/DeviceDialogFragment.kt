package com.example.pwrclipapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pwrclipapp.MainActivity
import com.example.pwrclipapp.databinding.FragmentDeviceDialogListDialogBinding
import com.example.pwrclipapp.mqtt.MQTTMessageCallback
import com.example.pwrclipapp.mqtt.Message
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.Format
import java.text.SimpleDateFormat


class DeviceDialogFragment : BottomSheetDialogFragment(), MQTTMessageCallback {

    private var _binding: FragmentDeviceDialogListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var lineGraphSeries: LineGraphSeries<DataPoint>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceDialogListDialogBinding.inflate(inflater, container, false)
        val bundle = arguments
        val position = bundle!!.getInt("position")

        val device = (activity as MainActivity).data[position]
        val msgList = (activity as MainActivity).msgList

        val imageView = binding.imageViewImage
        val textViewName = binding.textViewName
        val textViewOnlineSince = binding.textViewOnlineSince
        val textViewLastPing = binding.textViewLastPing
        val textViewCurrentUsage = binding.textViewCurrentUsage
        val graphViewUsage = binding.graphViewUsage

        imageView.setImageResource(device.image)
        textViewName.text = device.name
        if (!msgList.isEmpty()) {
            var formatter = SimpleDateFormat("dd-MM-yy HH:mm:ss")
            textViewOnlineSince.text = formatter.format(msgList[0].time)
            textViewLastPing.text = formatter.format(msgList.last().time)
            textViewCurrentUsage.text = msgList.last().value + "kWh"
        }

        lineGraphSeries = LineGraphSeries<DataPoint>(generateDataPointList(msgList))
        graphViewUsage.addSeries(lineGraphSeries)
        graphViewUsage.gridLabelRenderer.setLabelFormatter(object: DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                if (isValueX) {
                    val formatter: Format = SimpleDateFormat("HH:mm:ss");
                    return formatter.format(value)
                }
                return value.toInt().toString() + "kWh"//formatLabel(value, isValueX)
            }
        })
        graphViewUsage.gridLabelRenderer.setHumanRounding(true)
        graphViewUsage.gridLabelRenderer.setHorizontalLabelsAngle(135)
        graphViewUsage.viewport.isScalable = true
        graphViewUsage.viewport.setXAxisBoundsManual(true)
        graphViewUsage.viewport.setMinX(0.0)
        graphViewUsage.viewport.setMaxX(6000.0)
        graphViewUsage.viewport.isScrollable = false


        lineGraphSeries.setDrawDataPoints(true)
        lineGraphSeries.setDataPointsRadius(10F)
        lineGraphSeries.setThickness(8)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).connectMessageListener(this)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onDestroyView() {
        (activity as MainActivity).disconnectMessageListener()
        super.onDestroyView()
        _binding = null
    }
    override fun onMessageReceived(msg: Message) {
        activity?.runOnUiThread(Runnable {
            lineGraphSeries.appendData(generateDataPoint(msg), true, 40)
            try {
                var formatter = SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss")
                if (binding.textViewOnlineSince.text == "-") {
                    binding.textViewOnlineSince.text = formatter.format(msg.time)
                }
                binding.textViewLastPing.text = formatter.format(msg.time)
                binding.textViewCurrentUsage.text = msg.value + "W"
            }catch (e: Exception) {
                Log.d("myTag", e.toString());
            }
        })
    }

    fun generateDataPoint(msg: Message): DataPoint {
        return DataPoint(msg.time, msg.value.toDouble())
    }
    fun generateDataPointList(msgList: ArrayList<Message>): Array<DataPoint> {
        val dataPointList = Array<DataPoint>(msgList.count()) {DataPoint(0.0,0.0)}
        for (i: Int in 0..msgList.count()-1) {
            dataPointList.set(i,generateDataPoint(msgList[i]))
        }
        return dataPointList
    }
}