package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
        //Toast.makeText(this, "Welcome back, ${UserSession.user?.Name}!", Toast.LENGTH_SHORT).show()
        binding.bookingButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        binding.modifyTicket.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.ModifiyTicket")
            startActivity(intent)

        }
        binding.cancellationPolicy.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.CancellationPolicy")
            startActivity(intent)

        }
        binding.signOut.setOnClickListener {
            UserSession.clearSession()
            val intent = Intent(this, NewMainActivity::class.java)
            startActivity(intent)
        }
        binding.rateApp.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.RateThisApp")
            startActivity(intent)

        }
        binding.writeFeedback.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.RightFeedback")
            startActivity(intent)

        }
        binding.offers.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.Offers")
            startActivity(intent)
        }
        binding.privacyPolicy.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.PrivacyPolicy")
            startActivity(intent)

        }
        binding.termsAndConditions.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.TermsAndConditions")
            startActivity(intent)

        }
        binding.aboutUs.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.AboutUs")
            startActivity(intent)

        }
        binding.contactUs.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.myapplication.ProfilePack.ContactUs")
            startActivity(intent)
        }


        // Set up click listeners for the new options

    }
}