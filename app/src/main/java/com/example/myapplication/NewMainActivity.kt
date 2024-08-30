package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Network.RetrofitClient
import com.example.myapplication.databinding.ActivityNewMainBinding
import com.yourpackage.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val binding = ActivityNewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val userID = binding.editTextUserId.text.toString()
            val call = apiService.getUserDetails(userID)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val resUser = response.body()
                        if(resUser == null){
                            Toast.makeText(this@NewMainActivity, "User not found", Toast.LENGTH_SHORT).show()
                        }else if(resUser.password != binding.editTextPassword.text.toString()){
                            Toast.makeText(this@NewMainActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                        }else {
                            val intent = Intent(this@NewMainActivity, MainActivity::class.java)
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
}