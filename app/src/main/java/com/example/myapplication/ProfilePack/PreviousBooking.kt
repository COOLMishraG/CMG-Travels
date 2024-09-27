package com.example.myapplication.ProfilePack

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.Ticket
import com.example.myapplication.UserSession
import com.example.myapplication.databinding.ActivityPreviousBookingBinding
import com.yourpackage.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PreviousBooking : AppCompatActivity() {
    private lateinit var binding: ActivityPreviousBookingBinding
    private var ticketList = mutableListOf<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviousBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fetch Booking History
        fetchBookingHistory()
    }

    private fun fetchBookingHistory() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val userId = UserSession.user?.UserId.toString()

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@PreviousBooking, "Fetching booking history...", Toast.LENGTH_SHORT).show()
            }

            val response: Response<MutableList<String>> = apiService.getBookingHistory(userId).execute()

            if (response.isSuccessful && response.body() != null) {
                val bookingHistory = response.body()!!
                ticketList = mutableListOf()

                // Fetch ticket details for each booking in parallel
                val ticketDetailsDeferred = bookingHistory.map { pnr ->
                    async { fetchTicketDetails(userId, pnr) }
                }

                val tickets = ticketDetailsDeferred.awaitAll()

                // Filter out any null tickets and add to list
                tickets.filterNotNull().forEach { ticket ->
                    ticketList.add(ticket)
                }

                // Once data is fetched, update UI
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PreviousBooking, "Booking history fetched successfully", Toast.LENGTH_SHORT).show()
                    displayTickets()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PreviousBooking, "Failed to fetch booking history", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun fetchTicketDetails(userId: String, pnr: String): Ticket? {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        return try {
            val response: Response<Ticket> = apiService.getTicketDetails(userId, pnr).execute()
            if (response.isSuccessful && response.body() != null) {
                response.body() // Return the ticket
            } else {
                null // Return null if not successful
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@PreviousBooking, "Error fetching details for PNR $pnr: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            null // Return null on exception
        }
    }

    private fun displayTickets() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutTickets)

        // Clear any existing views
        linearLayout.removeAllViews()

        // Loop through ticketList and create views
        for (ticket in ticketList) {
            val ticketView = layoutInflater.inflate(R.layout.booking_ticket, linearLayout, false)

            val pnrTextView = ticketView.findViewById<TextView>(R.id.tvpnr)
            val fromTextView = ticketView.findViewById<TextView>(R.id.tvBoardingPoint01)
            val toTextView = ticketView.findViewById<TextView>(R.id.tvDestinationPoint01)
            val timeTextView = ticketView.findViewById<TextView>(R.id.tvDateOfTravel01)

            // Set the data for the ticket
            pnrTextView.text = "PNR: ${ticket.PNR}"
            fromTextView.text = "Boarding: ${ticket.From}"
            toTextView.text = "Destination: ${ticket.To}"
            timeTextView.text = "Time: ${ticket.departTime}"

            // Optionally set a click listener
            ticketView.setOnClickListener {
                onItemClick(ticket)
            }

            // Add the ticket view to the LinearLayout
            linearLayout.addView(ticketView)
        }
    }

    private fun onItemClick(ticket: Ticket) {
        // Handle click event on a ticket (e.g., navigate to ticket details page)
        Toast.makeText(this, "Clicked on PNR: ${ticket.PNR}", Toast.LENGTH_SHORT).show()

        // Navigate to ticket details page
        val intent = Intent(this, PreviousBookingDetailsActivity::class.java).apply {
            putExtra("PNR", ticket.PNR)
            putExtra("From", ticket.From)
            putExtra("To", ticket.To)
            putExtra("Price", ticket.Price)
            putExtra("BusNo" , ticket.BusNo)
            putExtra("departTime", ticket.departTime)
            putExtra("arrivalTime", ticket.arrivalTime)
            putExtra("Date", ticket.Name)

        }
        startActivity(intent)
    }
}
