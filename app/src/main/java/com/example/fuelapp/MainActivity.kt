






 package com.example.fuelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.github.kittinunf.fuel.httpGet

import com.github.kittinunf.fuel.gson.responseObject

import com.github.kittinunf.result.Result



data class Song (val ID: Int, val title: String, val artist: String, val year: Int, val chart: Int, val likes: Int, val downloads: Int, val review: String, val quantity: Int)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv1 = findViewById<TextView>(R.id.tv1)
        Log.d("fuel", "sending request")

        // 3 - use GSON


        val url = "http://10.0.2.2:3000/artist/Oasis"
        url.httpGet().responseObject<List<Song>> { request, response, result ->

            when(result) {
                is Result.Success -> {
                    // can't get data from response AND get data from result!
                    val text = result.get().map { "${it.title} by ${it.artist}, year ${it.year}"}.joinToString("\n")
                    tv1.text = text
                }

                is Result.Failure -> {
                    // is failure if HTTP error
                    Log.d("fuel", result.getException().message ?: "Unknown error")
                    tv1.text = "ERROR ${result.error}"
                }
            }

        }

    }
}