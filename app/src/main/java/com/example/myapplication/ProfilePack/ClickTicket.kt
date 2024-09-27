package com.example.myapplication.ProfilePack

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClickTicketBinding
import com.example.myapplication.databinding.ActivityPreviousBookingDetailsBinding

class ClickTicket : AppCompatActivity() {
    private lateinit var binding : ActivityClickTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClickTicketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityClickTicketBinding.inflate(layoutInflater) // Inflate the binding
        setContentView(binding.root) // Set the content view to the root of the binding

        // Get the data from the Intent
        val pnr = intent.getStringExtra("PNR")
        val from = intent.getStringExtra("From")
        val to = intent.getStringExtra("To")
        val price = intent.getStringExtra("Price")
        val busNo = intent.getStringExtra("BusNo")
        val departTime = intent.getStringExtra("departTime")
        val arrivalTime = intent.getStringExtra("arrivalTime")
        val date = intent.getStringExtra("Date")
        val name = intent.getStringExtra("Name") // Correcting from "Date" to "Name"

        // Set the data to the TextViews using binding
        binding.boardingStation.text = from // Set boarding station
        binding.destinationStation.text = to // Set destination station
        binding.ticketCost.text = price // Set ticket cost
        binding.busNumber.text = busNo // Set bus number
        binding.boardingTime.text = departTime // Set departure time
        binding.arrivalTime.text = arrivalTime // Set arrival time
        binding.ticketDate.text = date // Set ticket date
        binding.passengerName.text = name // Set passenger name
    }
}