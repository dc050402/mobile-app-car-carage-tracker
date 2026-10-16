package com.example.garage_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GarageAdapter(private val garages: List<Garage>,
                    private val onLongClick: (Garage) -> Unit
) : RecyclerView.Adapter<GarageAdapter.GarageViewHolder>() {

    class GarageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val addressText: TextView = itemView.findViewById(R.id.addressText)
        val ratingText: TextView = itemView.findViewById(R.id.ratingText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_garage, parent, false)
        return GarageViewHolder(view)
    }

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        val garage = garages[position]
        holder.nameText.text = garage.name
        holder.addressText.text = garage.address
        holder.ratingText.text = "Rating: ${garage.rating}"
        holder.itemView.setOnLongClickListener {
            onLongClick(garage)
            true
        }
    }

    override fun getItemCount(): Int = garages.size
}