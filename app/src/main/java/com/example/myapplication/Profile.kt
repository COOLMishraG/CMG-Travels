package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.ProfilePack.ModifiyTicket
import com.example.myapplication.ProfilePack.RateThisApp
import com.example.myapplication.ProfilePack.RightFeedback
import com.example.myapplication.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bookingButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.modifyTicket.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.ModifiyTicket")
            startActivity(intent)
            finish()
        }
        binding.cancellationPolicy.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.CancellationPolicy")
            startActivity(intent)
            finish()
        }
        binding.signOut.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.rateApp.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.RateThisApp")
            startActivity(intent)
            finish()
        }
        binding.writeFeedback.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.RightFeedback")
            startActivity(intent)
            finish()
        }
        binding.offers.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.Offers")
            startActivity(intent)
            finish()
        }
        binding.privacyPolicy.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.PrivacyPolicy")
            startActivity(intent)
            finish()
        }
        binding.termsAndConditions.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.TermsAndConditions")
            startActivity(intent)
            finish()
        }


        // Set up click listeners for the new options

    }
}