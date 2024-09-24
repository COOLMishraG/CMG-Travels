package com.example.myapplication

data class BusTicket(
    val id: String,
    val from: String,
    val to: String,
    val departureTime: String,
    val arrivalTime: String,
    val busNumber: String,
    val price: Int,
)

data class Ticket(
    val PNR:String,
    val Name:String,
    val From: String,
    val To: String,
    val BusNo: String,
    val departTime: String,
    val arrivalTime: String,
    val Price: Number
)

data class Booking(
    val pnr: String,
    val from: String,
    val to: String,
    val date: String,
    val price: String
)
