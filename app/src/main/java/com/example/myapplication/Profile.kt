package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.ProfilePack.ModifiyTicket
import com.example.myapplication.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    val binding : ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
        binding.offers.setOnClickListener {
            startActivity(Intent(this, Offers::class.java))
        }

        binding.writeFeedback.setOnClickListener {
            startActivity(Intent(this, WriteFeedback::class.java))
        }

        binding.readThisApp.setOnClickListener {
            startActivity(Intent(this, ReadThisApp::class.java))
        }

        binding.shareThisApp.setOnClickListener {
            startActivity(Intent(this, ShareThisApp::class.java))
        }

        binding.aboutUs.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        binding.privacyPolicy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicy::class.java))
        }

        binding.termsAndConditions.setOnClickListener {
            startActivity(Intent(this, TermsAndConditions::class.java))
        }

        binding.contactUs.setOnClickListener {
            startActivity(Intent(this, ContactUs::class.java))
        }

        binding.trackMyBus.setOnClickListener {
            startActivity(Intent(this, TrackMyBus::class.java))
        }
    }
}