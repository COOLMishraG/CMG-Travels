package com.example.myapplication.Weather

data class WeatherResponse(
    val weather: List<WeatherCondition>,
    val main: Main
)

data class WeatherCondition(
    val main: String,   // Example: "Clear", "Rain", etc.
    val icon: String    // Example: "01d" for sunny day icon
)

data class Main(
    val temp: Double    // Temperature in Kelvin (or metric if specified)
)
