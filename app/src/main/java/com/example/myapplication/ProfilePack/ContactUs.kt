package com.example.myapplication.ProfilePack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityContactUsBinding
import com.example.myapplication.databinding.ActivityProfileBinding

class ContactUs : AppCompatActivity() {
    private lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityContactUsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.githubLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://github.com/COOLMishraG/"))
            startActivity(intent)
        }
        binding.twitterLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://twitter.com/Mishra_Anuj04"))
            startActivity(intent)
        }
        binding.linkedinLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://www.linkedin.com/in/anuj-mishra-4330672b6"))
            startActivity(intent)
        }
        binding.instagramLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://www.instagram.com/anuj_o_mishra/"))
            startActivity(intent)
        }

        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}