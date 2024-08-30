package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BusActivity : AppCompatActivity() {
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

        // TODO: Fetch bus tickets based on from, to, and date
        val busTickets = fetchBusTickets(from, to, date)

        busAdapter = BusAdapter(busTickets, from, to, date)
        recyclerView.adapter = busAdapter
    }

    private fun fetchBusTickets(from: String, to: String, date: String): List<BusTicket> {
        // TODO: Implement actual data fetching logic
        return emptyList()
    }
}