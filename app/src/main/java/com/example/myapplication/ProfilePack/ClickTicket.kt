package com.example.myapplication.ProfilePack

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.R
import com.example.myapplication.Ticket
import com.example.myapplication.UserSession
import com.example.myapplication.databinding.ActivityClickTicketBinding
import com.yourpackage.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ClickTicket : AppCompatActivity() {
    private lateinit var binding: ActivityClickTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityClickTicketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        applyCustom(binding.bs)
        applyCustom(binding.at)
        applyCustom(binding.ds)
        applyCustom(binding.dt)
        applyCustom(binding.c)
        applyCustom(binding.d)
        applyCustom(binding.TOP)
        applyCustom(binding.tob)
        applyCustom(binding.n)
        applyCustom(binding.bn)
        applyCustom(binding.paymentType)
        applyCustom(binding.busType)




        val layout = binding.main
        val background = layout.background
        background.alpha = 25
        // Handle insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the PNR from the Intent
        val pnr = intent.getStringExtra("PNR")

        // Fetch ticket details using the PNR
        if (pnr != null) {
            fetchTicketDetails(pnr)
        } else {
            Toast.makeText(this, "No PNR found", Toast.LENGTH_SHORT).show()
        }

        val toolbar: Toolbar = binding.toolbar4
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.trackBusButton.setOnClickListener {
            val intent = Intent(this@ClickTicket , TrackingBus::class.java).apply {
                putExtra("BusName" , binding.busNumber.text.toString())
            }
            startActivity(intent)
        }

    }

    private fun createPdf(ticket: Ticket) {
        val pdfDocument = PdfDocument()

        // Create a page description
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()

        // Start a page
        val page = pdfDocument.startPage(pageInfo)

        // Draw content on the page (Canvas for drawing text)
        val canvas = page.canvas
        val paint = android.graphics.Paint()
        paint.textSize = 16F

        // Draw ticket details
        canvas.drawText("Ticket PNR: ${ticket.PNR}", 50F, 50F, paint)
        canvas.drawText("Name: ${ticket.Name}", 50F, 80F, paint)
        canvas.drawText("From: ${ticket.From}", 50F, 110F, paint)
        canvas.drawText("To: ${ticket.To}", 50F, 140F, paint)
        canvas.drawText("Bus No: ${ticket.BusNo}", 50F, 170F, paint)
        canvas.drawText("Departure: ${ticket.DepartureTime}", 50F, 200F, paint)
        canvas.drawText("Arrival: ${ticket.ArrivalTime}", 50F, 230F, paint)
        canvas.drawText("Price: $${ticket.Price}", 50F, 260F, paint)

        // Finish the page
        pdfDocument.finishPage(page)

        // Define the directory path
        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Tickets/"
        val directory = File(directoryPath)

        // Ensure the directory exists
        if (!directory.exists()) {
            directory.mkdirs() // Create the directory if it doesn't exist
        }

        // Define the file path and name
        val filePath = "$directoryPath/Ticket_${ticket.PNR}.pdf"
        val file = File(filePath)

        try {
            // Write the PDF to the file
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "PDF saved at $filePath", Toast.LENGTH_SHORT).show()
            openPdf(file)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        // Close the document
        pdfDocument.close()
    }


    private fun fetchTicketDetails(pnr: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val userId = UserSession.user?.UserId.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the API to get ticket details
                val response: Response<Ticket> = apiService.getTicketDetails(userId, pnr).execute()
                if (response.isSuccessful && response.body() != null) {
                    val ticket = response.body()

                    // Update the UI on the main thread
                    withContext(Dispatchers.Main) {
                        bindTicketData(ticket)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ClickTicket, "Failed to retrieve ticket details", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ClickTicket, "Error fetching ticket details: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindTicketData(ticket: Ticket?) {
        if (ticket != null) {
            // Apply styles to each TextView using the helper function
            applyCustomStyle(binding.boardingStation, ticket.From)
            applyCustomStyle(binding.destinationStation, ticket.To)
            applyCustomStyle(binding.ticketCost, ticket.Price.toString())
            applyCustomStyle(binding.busNumber, ticket.BusNo)
            applyCustomStyle(binding.boardingTime, ticket.DepartureTime)
            applyCustomStyle(binding.arrivalTime, ticket.ArrivalTime)
            applyCustomStyle(binding.ticketDate, ticket.PNR)
            applyCustomStyle(binding.paymentType ,payment_type.random())
            applyCustomStyle(binding.busType,busType.random())
            applyCustomStyle(binding.passengerName, UserSession.user?.Name ?: "")

            binding.downloadTicketButton.setOnClickListener {
                createPdf(ticket)
            }
        }
    }

    private fun openPdf(file: File) {
        // Create an intent to open the PDF file
        val intent = Intent(Intent.ACTION_VIEW)

        // Check the Android version
        val fileUri = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // Use a FileProvider to share the PDF file (for Android Nougat and above)
            val authority = "${applicationContext.packageName}.fileprovider"
            androidx.core.content.FileProvider.getUriForFile(this, authority, file)
        } else {
            // Use the file URI directly (for older versions)
            Uri.fromFile(file)
        }

        // Set the type of the intent as PDF
        intent.setDataAndType(fileUri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        // Try to start the activity for viewing the PDF
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No PDF viewer app found", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun applyCustomStyle(textView: TextView, text: String) {
        // Set text from ticket data
        textView.text = text

        // Apply custom font
        textView.typeface = ResourcesCompat.getFont(this, R.font.bungee)

        // Set padding
        val paddingInDp = (8 * resources.displayMetrics.density + 0.5f).toInt() // Convert 8dp to pixels
        textView.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp)

        // Set text color
        textView.setTextColor(Color.parseColor("#EB0404")) // Set the text color

        // Set text size
        textView.textSize = 16f // Set text size in sp
    }
    private fun applyCustom(textView: TextView) {
        // Set text from ticket data


        // Apply custom font
        textView.typeface = ResourcesCompat.getFont(this, R.font.bungee)

        // Set padding
        val paddingInDp = (8 * resources.displayMetrics.density + 0.5f).toInt() // Convert 8dp to pixels
        textView.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp)

        // Set text color
        textView.setTextColor(Color.parseColor("#10105C")) // Set the text color

        // Set text size
        textView.textSize = 16f // Set text size in sp
    }
    private val payment_type = mutableListOf("UPI" , "Card" , "Cash" , "netbanking" , "wallet")
    private val busType = mutableListOf("AC" , "AC+Sleeper" , "Sleeper" , "non-AC" , "2+1 sitting")
}
