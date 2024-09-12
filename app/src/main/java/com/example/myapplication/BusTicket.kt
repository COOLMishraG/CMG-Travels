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
    val Time: String,
    val From: String,
    val To: String,
    val BusNumber: String,
    val DeptTime: String,
    val Arrivetime: String,
    val cost: Number
)