package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTicketDetailBinding

class TicketDetailActivity : AppCompatActivity() {
    val binding: ActivityTicketDetailBinding by lazy {
        ActivityTicketDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val from = intent.getStringExtra("TICKET_FROM")
        val to = intent.getStringExtra("TICKET_TO")
        val date = intent.getStringExtra("TICKET_DATE")
        val arrivalTime = intent.getStringExtra("TICKET_ARRIVAL_TIME")
        val departureTime = intent.getStringExtra("TICKET_DEPARTURE_TIME")
        val BusNumber = intent.getStringExtra("TICKET_BUS_NUMBER")
        val price = intent.getIntExtra("TICKET_PRICE", 0)

        binding.textViewName.text = "Anuj"
        binding.textViewDepartingStation.text = from
        binding.textViewArrivingStation.text = to
        binding.textViewDepartingTime.text = departureTime
        binding.textViewArrivingTime.text = arrivalTime
        binding.textViewCost.text = price.toString()
        binding.textViewBusNumber.text = BusNumber
        binding.textViewDate.text = date
        binding.textViewPNR.text = generatePnr(arrivalTime!!,date!!,from!!)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun generatePnr(arrTime : String , date : String , src:String):String{
        val temp1 = arrTime.take(2).uppercase()
        val temp2 = src.take(2).uppercase()
        val temp3 = date.substring(8)
        return "$temp2-$temp3$temp1"
    }

}