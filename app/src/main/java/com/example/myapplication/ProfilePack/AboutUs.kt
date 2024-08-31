package com.example.myapplication.ProfilePack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAboutUsBinding

class AboutUs : AppCompatActivity() {
    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title

        // Set custom centered title
        binding.toolbarTitle.text = "About Us"
        binding.toolbarTitle.setTextColor(resources.getColor(android.R.color.white, theme))
        binding.toolbarTitle.textSize = 20f // 20sp

        // Set the status bar color to match the toolbar
        window.statusBarColor = resources.getColor(R.color.dark_blue, theme)


    }
}