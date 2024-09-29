package com.example.myapplication.ProfilePack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityOffersBinding

class Offers : AppCompatActivity() {
    private lateinit var binding: ActivityOffersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // Add your offers logic here
        loadOffers()
    }

    private fun loadOffers() {
        // Implement logic to load and display offers
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}