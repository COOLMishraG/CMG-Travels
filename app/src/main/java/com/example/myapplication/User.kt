package com.example.myapplication

data class User(
    val Name: String,
    val Phone: String,
    val email: String,
    val password: String,
    val UserId: String,
    val PNRs: MutableList<String>? = null,
)
