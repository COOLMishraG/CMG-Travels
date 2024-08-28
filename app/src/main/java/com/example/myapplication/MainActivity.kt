package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.DateSelector.setOnClickListener {
            Showdate()
        }
        binding.textView2.setOnClickListener {
            Showdate()
        }
        binding.imageView.setOnClickListener {
            if (binding.editTextText.text.toString().isEmpty()) {
                binding.editTextText.error = "Please enter a value"
            } else if (binding.editTextText2.text.toString().isEmpty()) {
                binding.editTextText2.error = "Please enter a value"
            } else {
                var temp = binding.editTextText.text.toString()
                binding.editTextText.setText(binding.editTextText2.text.toString())
                binding.editTextText2.setText(temp)
        }}
    }
    private fun Showdate(){
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the TextView with the selected date using binding
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.textView2.text = "DOJ:" + selectedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }
}