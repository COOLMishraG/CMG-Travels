package com.example.myapplication

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
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
            val pairs = arrayOf(android.util.Pair(binding.bookingButton1 as android.view.View, "img_anim"),
                android.util.Pair(binding.temp as android.view.View, "Wel_Come"))
            val option = ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
            startActivity(intent , option.toBundle())
            finish()

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
            signOut()
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
    private fun signOut() {
        // Clear the SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear() // This clears all the data
        editor.apply() // Apply the changes

        // Redirect to the login activity or wherever you need
        val intent = Intent(this, NewMainActivity::class.java)
        startActivity(intent)
        finish() // Optionally finish the current activity
    }
}