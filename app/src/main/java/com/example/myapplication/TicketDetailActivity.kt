package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.databinding.ActivityTicketDetailBinding
import com.yourpackage.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketDetailActivity : AppCompatActivity() {
    val binding: ActivityTicketDetailBinding by lazy {
        ActivityTicketDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val from = intent.getStringExtra("TICKET_FROM")
        val to = intent.getStringExtra("TICKET_TO")
        val date = intent.getStringExtra("TICKET_DATE")
        val arrivalTime = intent.getStringExtra("TICKET_ARRIVAL_TIME")
        val departureTime = intent.getStringExtra("TICKET_DEPARTURE_TIME")
        val BusNumber = intent.getStringExtra("TICKET_BUS_NUMBER")
        val price = intent.getIntExtra("TICKET_PRICE", 0)
        val Name = intent.getStringExtra("userName")

        binding.textViewName.text = Name
        binding.textViewDepartingStation.text = from
        binding.textViewArrivingStation.text = to
        binding.textViewDepartingTime.text = departureTime
        binding.textViewArrivingTime.text = arrivalTime
        binding.textViewCost.text = price.toString()
        binding.textViewBusNumber.text = BusNumber
        binding.textViewDate.text = date
        val PNRTEMP = generatePnr(arrivalTime!!,date!!,from!!)
        binding.textViewPNR.text = PNRTEMP
        val currUser = UserSession.user
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.button.setOnClickListener {
            if(binding.pass.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show()
            }else if(binding.pass.text.toString() != currUser!!.password){
                Toast.makeText(this,"Incorrect Password ${currUser.password}",Toast.LENGTH_SHORT).show()
            }else if(binding.checkboxDetailsRevised.isChecked == false){
                Toast.makeText(this,"Please check the checkbox",Toast.LENGTH_SHORT).show()
            }else{
                val currTicket = Ticket(PNRTEMP , currUser.Name , from , to!! , BusNumber!! , departureTime!!, arrivalTime , price )
                val apiService = RetrofitClient.instance.create(ApiService::class.java)
                val give = apiService.createTicket(currUser.UserId,currTicket)
                give.enqueue(object : Callback<Ticket> {
                    @SuppressLint("SuspiciousIndentation")
                    override fun onResponse(call: Call<Ticket>, response: Response<Ticket>) {
                        if(response.isSuccessful){
                        val resTicket = response.body()
                        val ticketPnr = resTicket?.PNR
                            Toast.makeText(this@TicketDetailActivity, "Ticket Booked Successfully $ticketPnr", Toast.LENGTH_SHORT).show()

                        }else {
                            Toast.makeText(this@TicketDetailActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }}
                    override fun onFailure(call: Call<Ticket>, t: Throwable) {
                        Toast.makeText(this@TicketDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()

                    }

                })
                Toast.makeText(this,"Booking Confirmed",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun generatePnr(arrTime : String , date : String , src:String):String{
        val temp1 = arrTime.take(2).uppercase()
        val temp2 = src.take(2).uppercase()
        val temp3 = date.substring(8)
        return "$temp2-$temp3$temp1"
    }

}