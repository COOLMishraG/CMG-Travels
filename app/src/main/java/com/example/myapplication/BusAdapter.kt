package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BusAdapter(
    private val busTickets: List<BusTicket>,
    private val fromStation: String?,
    private val toStation: String?,
    private val selectedDate: String?
) : RecyclerView.Adapter<BusAdapter.BusViewHolder>() {

    class BusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val route: TextView = itemView.findViewById(R.id.textViewRoute)
        val date: TextView = itemView.findViewById(R.id.textViewDate)
        val time: TextView = itemView.findViewById(R.id.textViewTime)
        val price: TextView = itemView.findViewById(R.id.textViewPrice)
        val bookButton: Button = itemView.findViewById(R.id.buttonBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_ticket_item, parent, false)
        return BusViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val ticket = busTickets[position]
        if (matchesFilter(ticket)) {
            holder.route.text = "${ticket.from} to ${ticket.to}"

            holder.time.text = "${ticket.departureTime} - ${ticket.arrivalTime}"
            holder.price.text = "₹${ticket.price}"
            holder.itemView.visibility = View.VISIBLE
        } else {
            holder.itemView.visibility = View.GONE
        }
    }

    private fun matchesFilter(ticket: BusTicket): Boolean {
        return (fromStation == null || ticket.from == fromStation) &&
               (toStation == null || ticket.to == toStation)

    }

    override fun getItemCount() = busTickets.size
}