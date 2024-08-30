package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class Bus : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var busAdapter: BusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)

        recyclerView = findViewById(R.id.recyclerViewBuses)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val from = intent.getStringExtra("from") ?: ""
        val to = intent.getStringExtra("to") ?: ""
        val date = intent.getStringExtra("date") ?: ""

        val busTickets = fetchBusTickets(from, to, date)

        busAdapter = BusAdapter(busTickets, from, to, date)
        recyclerView.adapter = busAdapter
    }

    private fun fetchBusTickets(from: String, to: String, date: String): List<BusTicket> {
        // Sample data of buses with various routes and specific dates
        val allTickets = listOf(
            BusTicket("1", "Indore", "Bhopal", "2023-08-10", "06:00", "09:30", 350.0),
            BusTicket("2", "Indore", "Bhopal", "2023-08-10", "08:30", "12:00", 380.0),
            BusTicket("3", "Bhopal", "Ujjain", "2023-08-10", "07:00", "09:30", 250.0),
            BusTicket("4", "Bhopal", "Ujjain", "2023-08-12", "10:30", "13:00", 270.0),
            BusTicket("5", "Ujjain", "Sihor", "2023-08-12", "09:00", "11:30", 200.0),
            BusTicket("6", "Ujjain", "Sihor", "2023-08-12", "14:00", "16:30", 220.0),
            BusTicket("7", "Sihor", "Sagar", "2023-08-15", "08:00", "11:30", 300.0),
            BusTicket("8", "Sihor", "Sagar", "2023-08-15", "13:00", "16:30", 320.0),
            BusTicket("9", "Sagar", "Indore", "2023-08-15", "07:30", "12:00", 400.0),
            BusTicket("10", "Sagar", "Indore", "2023-08-15", "15:00", "19:30", 420.0)
        )

        return allTickets.filter { ticket ->
            ticket.from == from && ticket.to == to && ticket.date == date
        }.sortedBy { it.departureTime }
    }
}