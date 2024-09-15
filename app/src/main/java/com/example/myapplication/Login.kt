package com.example.myapplication

import android.content.Intent
import android.os.Build.VERSION_CODES.N
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.gson.Gson
import com.yourpackage.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Set up button click listener
        binding.buttonCreateUser.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            val userId = binding.editTextUserId.text.toString()
            if(name.isEmpty() ||  email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userId.isEmpty() || phone.isEmpty()){
                  Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }else if(password != confirmPassword){
                 Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }else {
                val TempUSer = User(name , phone, email, password, userId)
                val apiService = RetrofitClient.instance.create(ApiService::class.java)
                val give = apiService.createUser(TempUSer)
                UserSession.user = TempUSer
                UserSession.isLoggedIn = true

                give.enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful){
                            val resUser = response.body()
                            val userName = resUser?.Name
                            Toast.makeText(this@Login, "User created successfully $userName", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Login, NewMainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this@Login, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@Login, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val gson = Gson()
                editor.putBoolean("isLoggedIn", true)
                val userJson = gson.toJson(TempUSer)
                editor.putString("user", userJson)
                editor.apply()

            }
        }
    }


}