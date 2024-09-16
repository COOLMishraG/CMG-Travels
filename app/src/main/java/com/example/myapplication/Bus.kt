package com.example.myapplication

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
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
        enableEdgeToEdge()

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
        var allTickets = listOf(
             //Existing tickets

            BusTicket("100", "Jabalpur", "Balaghat", "08:00", "12:00", "MPINGW001" , 300 )
        )
        allTickets = generateBusTickets()
        return allTickets.filter { ticket ->
            ticket.from == from && ticket.to == to
        }.sortedBy { it.departureTime }
    }
    fun generateBusTickets(): List<BusTicket> {
        val cities = listOf("Indore", "Bhopal", "Ujjain", "Sihor", "Sagar", "Gwalior", "Jabalpur", "Rewa", "Ratlam", "Chhindwara")
        val allTickets = mutableListOf<BusTicket>()
        var ticketId = 1
        val busNumberCounters = mutableMapOf<String, Int>()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        fun generateBusNumber(source: String, destination: String): String {
            val sourceCode = source.take(2).uppercase()
            val destCode = destination.take(2).uppercase()
            val routeKey = "$sourceCode-$destCode"
            val counter = busNumberCounters.getOrDefault(routeKey, 0) + 1
            busNumberCounters[routeKey] = counter
            return "MP-$sourceCode$destCode-${String.format("%03d", counter)}"
        }

        for (source in cities) {
            for (destination in cities) {
                if (source != destination) {
                    for (hour in 6..23) {
                        for (minute in listOf(0, 15, 30, 45)) {
                            val departureTime = String.format("%02d:%02d", hour, minute)
                            val arrivalHour = (hour + (1..6).random()) % 24
                            val arrivalMinute = listOf(0, 30).random()
                            val arrivalTime = String.format("%02d:%02d", arrivalHour, arrivalMinute)
                            val fare = (200..600).random()
                            val busNumber = generateBusNumber(source, destination)

                            allTickets.add(
                                BusTicket(
                                    ticketId.toString(),
                                    source,
                                    destination,
                                    departureTime,
                                    arrivalTime,
                                    busNumber,
                                    fare
                                )
                            )

                            ticketId++

                            if (allTickets.size >= 5000) break
                        }
                        if (allTickets.size >= 5000) break
                    }
                }
                if (allTickets.size >= 5000) break
            }
            if (allTickets.size >= 5000) break
        }

        return allTickets
    }


}