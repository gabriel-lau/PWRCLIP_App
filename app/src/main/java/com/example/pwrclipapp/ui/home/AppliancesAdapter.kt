package com.example.pwrclipapp.ui.home;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pwrclipapp.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

public class AppliancesAdapter(private val dataSet: ArrayList<AppliancesViewModel>, private val onNoteListener: OnNoteListener) : RecyclerView.Adapter<AppliancesAdapter.ViewHolder>(){
    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        val textViewName: TextView
        val textViewStatus: TextView
        val textViewUsage: TextView
        val imageViewImage: ImageView
        val cardViewAppliance: MaterialCardView

        init {
            // Define click listener for the ViewHolder's View
            textViewName = view.findViewById(R.id.textView_Name)
            textViewStatus = view.findViewById(R.id.textView_Status)
            textViewUsage = view.findViewById(R.id.textView_Usage)
            imageViewImage = view.findViewById(R.id.imageView_Image)
            cardViewAppliance = view.findViewById(R.id.cardView_Appliance)
            cardViewAppliance.setOnClickListener(this)
            cardViewAppliance.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            onNoteListener.onNoteClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            if (v != null) {
                MaterialAlertDialogBuilder(v.rootView.context)
                    .setTitle("Delete row")
                    .setMessage("Are you sure you want to delete this row?")
                    .setNegativeButton("Yes") { dialog, which ->
                        dataSet.removeAt(position)
                        notifyItemRemoved(position);
                    }
                    .setPositiveButton("No") { dialog, which ->

                    }
                    .show()
            }
            return true
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_home_appliance, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewName.text = dataSet[position].name
        viewHolder.textViewStatus.text = dataSet[position].status
        viewHolder.textViewUsage.text = dataSet[position].usage
        viewHolder.imageViewImage.setImageResource(dataSet[position].image)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

