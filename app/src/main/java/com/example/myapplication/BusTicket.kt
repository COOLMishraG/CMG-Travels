package com.example.myapplication

data class BusTicket(
    val id: String,
    val From: String,
    val To: String,
    val DepartureTime: String,
    val ArrivalTime: String,
    val BusNo: String,
    val Price: Int,
)

data class Ticket(
    val PNR:String,
    val Name:String,
    val From: String,
    val To: String,
    val BusNo: String,
    val DepartureTime: String,
    val ArrivalTime: String,
    val Price: Number
)

data class Booking(
    val pnr: String,
    val from: String,
    val to: String,
    val date: String,
    val price: String
)
