package com.example.myapplication

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Login
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySplashBinding
import com.google.gson.Gson


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //hideSystemUI()
        // Load animations
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeInDelayedAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_delayed)

        // Start animations
        binding.splashLogo.startAnimation(rotateAnimation)
       //binding.appName.startAnimation(fadeInAnimation)
        // Navigate to LoginActivity after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)

            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                val gson = Gson()
                val userJson = sharedPreferences.getString("user", null)

                if (userJson != null) {
                    val user = gson.fromJson(userJson, User::class.java)

                    // Populate UserSession
                    UserSession.isLoggedIn = true
                    UserSession.user = user
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    // Use the data
                    Toast.makeText(this, "Welcome back, ${user.Name}! ${user.password}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Redirect to login if no user is logged in
                val intent = Intent(this@SplashActivity, NewMainActivity::class.java)
                val pairs = arrayOf(android.util.Pair(binding.splashLogo as View, "logo_image")) // Use android.util.Pair
                val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity, *pairs)
                startActivity(intent, options.toBundle())
            }
        }, 4000)

    }
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}