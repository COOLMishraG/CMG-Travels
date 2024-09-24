package com.yourpackage.network

import com.example.myapplication.Ticket
import com.example.myapplication.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("user/{userId}")
    fun getUserDetails(@Path("userId") userId: String): Call<User>

    @POST("user")
    fun createUser(@Body userIn: User): Call<User>

    @POST("{UserId}/ticket")
    fun createTicket(@Path("UserId") UserId: String, @Body ticket: Ticket): Call<Ticket>

    @GET("{UserId}/Booking")
    fun getBookingHistory(@Path("UserId") UserId: String): Call<MutableList<String>>

    @GET("{UserId}/{ticket}")
    fun getTicketDetails(@Path("UserId") UserId: String, @Path("ticket") ticket: String): Call<Ticket>

}