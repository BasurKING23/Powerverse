package com.angel.powerverse

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getretrofit()
        }
        fun getretrofit(){
            val retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/7606f1c6a2b57ea22ca4bdf432bd1f9c")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(SuperheroService::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val result = service.findSuperheroById("23")
                Log.i("API", "${result.id}->${result.name}")
            }
        }
}
