package com.example.myapplication

data class BusTicket(
    val id: String,
    val from: String,
    val to: String,
    val date: String,
    val departureTime: String,
    val arrivalTime: String,
    val price: Double
)