package com.example.myapplication.ProfilePack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRateThisAppBinding

class RateThisApp : AppCompatActivity() {
    private lateinit var binding: ActivityRateThisAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateThisAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title

        // Set custom centered title
        val titleTextView = binding.toolbarTitle
        titleTextView.text = "Rate This App"

        // Set the status bar color to match the toolbar
        window.statusBarColor = resources.getColor(R.color.dark_blue, theme)

        // Handle submit button click
        binding.submitButton.setOnClickListener {
            val rating = binding.ratingBar.rating
            // Handle the rating submission here
        }
    }
}