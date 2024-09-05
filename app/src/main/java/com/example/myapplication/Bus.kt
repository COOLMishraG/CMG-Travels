package com.example.myapplication

import android.os.Bundle
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
            // Existing tickets
            BusTicket("1", "Indore", "Bhopal", "06:00", "09:30", 350.0),
            BusTicket("2", "Indore", "Bhopal", "08:30", "12:00", 380.0),
            BusTicket("3", "Bhopal", "Ujjain", "07:00", "09:30", 250.0),
            BusTicket("4", "Bhopal", "Ujjain", "10:30", "13:00", 270.0),
            BusTicket("5", "Ujjain", "Sihor", "09:00", "11:30", 200.0),
            BusTicket("6", "Ujjain", "Sihor", "14:00", "16:30", 220.0),
            BusTicket("7", "Sihor", "Sagar", "08:00", "11:30", 300.0),
            BusTicket("8", "Sihor", "Sagar", "13:00", "16:30", 320.0),
            BusTicket("9", "Sagar", "Indore", "07:30", "12:00", 400.0),
            BusTicket("10", "Sagar", "Indore", "15:00", "19:30", 420.0),

            // Previously added tickets
            BusTicket("11", "Jabalpur", "Gwalior", "05:30", "11:00", 550.0),
            BusTicket("12", "Gwalior", "Jabalpur", "14:00", "19:30", 550.0),
            BusTicket("13", "Indore", "Jabalpur", "07:00", "13:30", 480.0),
            BusTicket("14", "Jabalpur", "Indore", "15:00", "21:30", 480.0),
            BusTicket("15", "Bhopal", "Gwalior", "06:30", "11:00", 400.0),
            BusTicket("16", "Gwalior", "Bhopal", "13:30", "18:00", 400.0),
            BusTicket("17", "Rewa", "Satna", "08:00", "10:00", 150.0),
            BusTicket("18", "Satna", "Rewa", "16:00", "18:00", 150.0),
            BusTicket("19", "Indore", "Ratlam", "07:30", "10:30", 220.0),
            BusTicket("20", "Ratlam", "Indore", "14:30", "17:30", 220.0),
            BusTicket("21", "Bhopal", "Hoshangabad", "09:00", "11:00", 180.0),
            BusTicket("22", "Hoshangabad", "Bhopal", "15:00", "17:00", 180.0),
            BusTicket("23", "Jabalpur", "Rewa", "08:30", "13:30", 350.0),
            BusTicket("24", "Rewa", "Jabalpur", "14:30", "19:30", 350.0),
            BusTicket("25", "Gwalior", "Morena", "07:00", "09:00", 150.0),
            BusTicket("26", "Morena", "Gwalior", "17:00", "19:00", 150.0),
            BusTicket("27", "Indore", "Ujjain", "08:00", "09:30", 120.0),
            BusTicket("28", "Ujjain", "Indore", "18:00", "19:30", 120.0),
            BusTicket("29", "Bhopal", "Vidisha", "10:00", "11:30", 100.0),
            BusTicket("30", "Vidisha", "Bhopal", "16:00", "17:30", 100.0),
            BusTicket("31", "Jabalpur", "Mandla", "09:30", "12:30", 220.0),
            BusTicket("32", "Mandla", "Jabalpur", "15:30", "18:30", 220.0),
            BusTicket("33", "Sagar", "Damoh", "11:00", "13:00", 150.0),
            BusTicket("34", "Damoh", "Sagar", "16:00", "18:00", 150.0),
            BusTicket("35", "Indore", "Dewas", "07:30", "08:30", 80.0),
            BusTicket("36", "Dewas", "Indore", "19:00", "20:00", 80.0),
            BusTicket("37", "Bhopal", "Sehore", "09:30", "10:30", 70.0),
            BusTicket("38", "Sehore", "Bhopal", "17:30", "18:30", 70.0),
            BusTicket("39", "Gwalior", "Bhind", "08:30", "10:30", 140.0),
            BusTicket("40", "Bhind", "Gwalior", "16:30", "18:30", 140.0),

            // Additional tickets to reach 100
            BusTicket("41", "Indore", "Bhopal", "14:00", "17:30", 360.0),
            BusTicket("42", "Bhopal", "Indore", "07:00", "10:30", 360.0),
            BusTicket("43", "Bhopal", "Indore", "15:30", "19:00", 370.0),
            BusTicket("44", "Jabalpur", "Bhopal", "06:00", "10:30", 400.0),
            BusTicket("45", "Bhopal", "Jabalpur", "14:00", "18:30", 400.0),
            BusTicket("46", "Gwalior", "Indore", "05:00", "12:00", 600.0),
            BusTicket("47", "Indore", "Gwalior", "13:00", "20:00", 600.0),
            BusTicket("48", "Ujjain", "Bhopal", "07:30", "10:00", 250.0),
            BusTicket("49", "Bhopal", "Ujjain", "16:00", "18:30", 250.0),
            BusTicket("50", "Rewa", "Bhopal", "06:00", "12:30", 450.0),
            BusTicket("51", "Bhopal", "Rewa", "14:00", "20:30", 450.0),
            BusTicket("52", "Indore", "Khargone", "08:00", "11:00", 220.0),
            BusTicket("53", "Khargone", "Indore", "15:00", "18:00", 220.0),
            BusTicket("54", "Bhopal", "Itarsi", "09:30", "11:30", 160.0),
            BusTicket("55", "Itarsi", "Bhopal", "16:30", "18:30", 160.0),
            BusTicket("56", "Jabalpur", "Katni", "07:30", "09:30", 150.0),
            BusTicket("57", "Katni", "Jabalpur", "17:30", "19:30", 150.0),
            BusTicket("58", "Gwalior", "Datia", "10:00", "11:30", 100.0),
            BusTicket("59", "Datia", "Gwalior", "15:00", "16:30", 100.0),
            BusTicket("60", "Indore", "Mhow", "08:30", "09:30", 60.0),
            BusTicket("61", "Mhow", "Indore", "18:30", "19:30", 60.0),
            BusTicket("62", "Bhopal", "Raisen", "11:00", "12:30", 90.0),
            BusTicket("63", "Raisen", "Bhopal", "15:00", "16:30", 90.0),
            BusTicket("64", "Ujjain", "Dewas", "09:00", "10:00", 70.0),
            BusTicket("65", "Dewas", "Ujjain", "17:00", "18:00", 70.0),
            BusTicket("66", "Sagar", "Bina", "10:30", "12:00", 110.0),
            BusTicket("67", "Bina", "Sagar", "16:30", "18:00", 110.0),
            BusTicket("68", "Ratlam", "Mandsaur", "08:00", "10:30", 180.0),
            BusTicket("69", "Mandsaur", "Ratlam", "15:00", "17:30", 180.0),
            BusTicket("70", "Jabalpur", "Seoni", "11:00", "13:30", 190.0),
            BusTicket("71", "Seoni", "Jabalpur", "16:00", "18:30", 190.0),
            BusTicket("72", "Gwalior", "Shivpuri", "07:30", "10:00", 200.0),
            BusTicket("73", "Shivpuri", "Gwalior", "15:30", "18:00", 200.0),
            BusTicket("74", "Indore", "Dhar", "09:30", "11:30", 140.0),
            BusTicket("75", "Dhar", "Indore", "16:30", "18:30", 140.0),
            BusTicket("76", "Bhopal", "Betul", "07:00", "11:00", 300.0),
            BusTicket("77", "Betul", "Bhopal", "14:00", "18:00", 300.0),
            BusTicket("78", "Ujjain", "Ratlam", "10:00", "12:00", 150.0),
            BusTicket("79", "Ratlam", "Ujjain", "15:00", "17:00", 150.0),
            BusTicket("80", "Sagar", "Chhatarpur", "08:30", "11:30", 220.0),
            BusTicket("81", "Chhatarpur", "Sagar", "15:30", "18:30", 220.0),
            BusTicket("82", "Indore", "Khandwa", "07:00", "11:00", 300.0),
            BusTicket("83", "Khandwa", "Indore", "14:00", "18:00", 300.0),
            BusTicket("84", "Bhopal", "Shajapur", "09:00", "11:30", 180.0),
            BusTicket("85", "Shajapur", "Bhopal", "16:00", "18:30", 180.0),
            BusTicket("86", "Jabalpur", "Narsinghpur", "10:30", "12:30", 150.0),
            BusTicket("87", "Narsinghpur", "Jabalpur", "15:30", "17:30", 150.0),
            BusTicket("88", "Gwalior", "Guna", "08:00", "11:00", 230.0),
            BusTicket("89", "Guna", "Gwalior", "15:00", "18:00", 230.0),
            BusTicket("90", "Rewa", "Sidhi", "09:30", "11:30", 160.0),
            BusTicket("91", "Sidhi", "Rewa", "16:30", "18:30", 160.0),
            BusTicket("92", "Indore", "Burhanpur", "06:30", "10:30", 300.0),
            BusTicket("93", "Burhanpur", "Indore", "14:30", "18:30", 300.0),
            BusTicket("94", "Bhopal", "Rajgarh", "08:30", "11:00", 190.0),
            BusTicket("95", "Rajgarh", "Bhopal", "15:30", "18:00", 190.0),
            BusTicket("96", "Ujjain", "Agar Malwa", "11:00", "13:00", 140.0),
            BusTicket("97", "Agar Malwa", "Ujjain", "16:00", "18:00", 140.0),
            BusTicket("98", "Sagar", "Tikamgarh", "07:30", "11:00", 260.0),
            BusTicket("99", "Tikamgarh", "Sagar", "14:30", "18:00", 260.0),
            BusTicket("100", "Jabalpur", "Balaghat", "08:00", "12:00", 300.0)
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

        for (source in cities) {
            for (destination in cities) {
                if (source != destination) {
                    // Loop through the hours of the day
                    for (hour in 6..23) {
                        // Add bus schedules in 15-minute and 30-minute intervals
                        for (minute in listOf(0, 15, 30, 45)) {
                            // Generate departure time
                            val departureTime = String.format("%02d:%02d", hour, minute)

                            // Randomly generate arrival time between 1 and 6 hours after departure
                            val arrivalHour = (hour + (1..6).random()) % 24
                            val arrivalMinute = listOf(0, 30).random()  // Arrival can also be at 00 or 30 minutes
                            val arrivalTime = String.format("%02d:%02d", arrivalHour, arrivalMinute)

                            // Random fare based on distance (between 200 and 600)
                            val fare = (200..600).random().toDouble()

                            // Add new bus ticket to the list
                            allTickets.add(
                                BusTicket(
                                    ticketId.toString(),
                                    source,
                                    destination,
                                    departureTime,
                                    arrivalTime,
                                    fare
                                )
                            )

                            ticketId++

                            // Stop at 500 tickets
                            if (allTickets.size >= 5000) {
                                break
                            }
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