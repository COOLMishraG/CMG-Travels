package com.example.myapplication

import android.content.Intent
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
        val routeTo: TextView = itemView.findViewById(R.id.textViewRouteTo)
        val routeFrom: TextView = itemView.findViewById(R.id.textViewRouteFrom)
        val date: TextView = itemView.findViewById(R.id.textViewDate)
        val time: TextView = itemView.findViewById(R.id.textViewArrivalTime)
        val DeptTime: TextView = itemView.findViewById(R.id.textViewDepartureTime)
        val price: TextView = itemView.findViewById(R.id.textViewPrice)
        val bookButton: Button = itemView.findViewById(R.id.buttonBook)
        val busnumber: TextView = itemView.findViewById(R.id.BusNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_ticket_item, parent, false)
        return BusViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val ticket = busTickets[position]
        if (matchesFilter(ticket)) {
            holder.routeTo.text = ticket.To
            holder.routeFrom.text = ticket.From
            holder.date.text = selectedDate
            holder.time.text = ticket.ArrivalTime
            holder.DeptTime.text = ticket.DepartureTime
            holder.price.text = "₹${ticket.Price}"
            holder.busnumber.text = ticket.BusNo
            holder.itemView.visibility = View.VISIBLE

            // Set click listener for the book button
            holder.bookButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, TicketDetailActivity::class.java).apply {
                    putExtra("userName" , UserSession.user?.Name)
                    putExtra("TICKET_FROM", ticket.From)
                    putExtra("TICKET_TO", ticket.To)
                    putExtra("TICKET_DATE", selectedDate)
                    putExtra("TICKET_ARRIVAL_TIME", ticket.ArrivalTime)
                    putExtra("TICKET_DEPARTURE_TIME", ticket.DepartureTime)
                    putExtra("TICKET_BUS_NUMBER", ticket.BusNo)
                    putExtra("TICKET_PRICE", ticket.Price)
                }
                context.startActivity(intent)
            }
        }else {
            holder.itemView.visibility = View.GONE
        }
    }

    private fun matchesFilter(ticket: BusTicket): Boolean {
        return (fromStation == null || ticket.From == fromStation) &&
                (toStation == null || ticket.To == toStation)
    }

    override fun getItemCount() = busTickets.size
}