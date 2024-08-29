package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityBoardingBinding

class Boarding : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingBinding
    private lateinit var stationList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example data: List of station names
        stationList = listOf(
            "Bhopal", "Indore", "Gwalior", "Jabalpur", "Ujjain",
            "Sagar", "Rewa", "Satna", "Ratlam", "Morena",
            "Neemuch", "Chhindwara", "Shahdol", "Dewas", "Sehore",
            "Vidisha", "Raisen", "Khargone", "Khandwa", "Shivpuri",
            "Bhind", "Guna", "Datia", "Tikamgarh", "Mandla",
            "Hoshangabad", "Betul", "Mandsaur", "Chhatarpur", "Balaghat"
        )

        // Set up the adapter for ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stationList)
        binding.listViewStations.adapter = adapter

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        // Handle item selection
        binding.listViewStations.setOnItemClickListener { _, _, position, _ ->
            val selectedStation = adapter.getItem(position)
            val requestCode = intent.getIntExtra("requestCode", -1)

            val resultIntent = Intent().apply {
                putExtra("selectedStation", selectedStation)
                putExtra("requestCode", requestCode)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
