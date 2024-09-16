package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.databinding.ActivityNewMainBinding
import com.google.gson.Gson
import com.yourpackage.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //hideSystemUI()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            val pairs = arrayOf(android.util.Pair(binding.imageView2 as View, "img_anim"),
                android.util.Pair(binding.textView3 as View, "Wel_Come"),
                android.util.Pair(binding.textView4 as View, "Wel_Come01"),
                android.util.Pair(binding.textInputLayout2 as View, "PhoneIn"),
                android.util.Pair(binding.textInputLayout as View, "UserIN"),
                )
            val options = android.app.ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
            startActivity(intent, options.toBundle())

        }
        binding.buttonLogin.setOnClickListener {
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val userID = binding.UserIn.text.toString()
            val password = binding.passIn.text.toString()
            val call = apiService.getUserDetails(userID)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val resUser = response.body()
                        if(resUser == null){
                            Toast.makeText(this@NewMainActivity, "User not found", Toast.LENGTH_SHORT).show()
                        }else if(resUser.password != password){
                            Toast.makeText(this@NewMainActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                        }else {
                            val intent = Intent(this@NewMainActivity, MainActivity::class.java)
                            UserSession.user = resUser
                            UserSession.isLoggedIn = true
                            val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("isLoggedIn", true)
                            val gson = Gson()
                            val userJson = gson.toJson(resUser)
                            editor.putString("user", userJson)
                            editor.apply()
                            startActivity(intent)
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@NewMainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
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