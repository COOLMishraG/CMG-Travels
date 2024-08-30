package com.example.myapplication

data class User(
    val Name: String,
    val Phone: Number,
    val email: String,
    val password: String,
    val UserId: String,
    val PNRs: MutableList<Number>? = null,

)
