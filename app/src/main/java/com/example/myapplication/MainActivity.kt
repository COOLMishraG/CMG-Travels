package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val getStationResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val selectedStation = data?.getStringExtra("selectedStation")
                val requestCode = data?.getIntExtra("requestCode", -1) ?: -1

                // Update the appropriate EditText based on the requestCode
                when (requestCode) {
                    SOURCE_STATION_REQUEST_CODE -> {
                        binding.editTextText2.setText(selectedStation)
                    }
                    DESTINATION_STATION_REQUEST_CODE -> {
                        binding.editTextText.setText(selectedStation)
                    }
                }
            }
        }

    companion object {
        private const val SOURCE_STATION_REQUEST_CODE = 1
        private const val DESTINATION_STATION_REQUEST_CODE = 2
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.profileButton.setOnClickListener {
         val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
        binding.Searchbuses.setOnClickListener{
            val From = binding.editTextText2.text.toString()
            val To = binding.editTextText.text.toString()
            val DOJ = binding.textView2.text.toString()
            if (From.isEmpty() || To.isEmpty() || DOJ.isEmpty()) {
                if (From.isEmpty()) {
                    binding.editTextText2.error = "Please enter a value"
                }
                if (To.isEmpty()) {
                    binding.editTextText.error = "Please enter a value"
                    }
                if (DOJ.isEmpty()) {
                    binding.textView2.error = "Please enter a value"
                }
                } else {
                val intent = Intent(this, Bus::class.java)
                intent.putExtra("from", From)
                intent.putExtra("to", To)
                intent.putExtra("date", DOJ)
                Log.d("MainActivity", "From: $From, To: $To, DOJ: $DOJ")

                startActivity(intent)
            }
        }
        // Get the selected station name from the intent
        val selectedStation = intent.getStringExtra("selectedStation")
        if (selectedStation != null) {
            binding.editTextText2.setText(selectedStation)
        } else {
            binding.editTextText2.setHint("From: Not Selected")
        }

        // Set up Date Selector click listener
        binding.DateSelector.setOnClickListener {
            showDate()
        }

        // Set up Source Station EditText click listener to open SpinnerActivity
        binding.editTextText.setOnClickListener {
            val intent = Intent(this, Boarding::class.java)
            intent.putExtra("requestCode", DESTINATION_STATION_REQUEST_CODE)
            getStationResult.launch(intent)
        }

        // Set up Destination Station EditText click listener to open SpinnerActivity
        binding.editTextText2.setOnClickListener {
            val intent = Intent(this, Boarding::class.java)
            intent.putExtra("requestCode", SOURCE_STATION_REQUEST_CODE)
            getStationResult.launch(intent)
        }

        // Set up imageView click listener to swap text between editTextText and editTextText2
        binding.imageView.setOnClickListener {
            if (binding.editTextText.text.toString().isEmpty()) {
                binding.editTextText.error = "Please enter a value"
            } else if (binding.editTextText2.text.toString().isEmpty()) {
                binding.editTextText2.error = "Please enter a value"
            } else {
                val temp = binding.editTextText.text.toString()
                binding.editTextText.setText(binding.editTextText2.text.toString())
                binding.editTextText2.setText(temp)
            }
        }
    }

    private fun showDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the TextView with the selected date using binding
                val selectedDate = "$selectedYear-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDay)}"
                binding.textView2.text = selectedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }
}
