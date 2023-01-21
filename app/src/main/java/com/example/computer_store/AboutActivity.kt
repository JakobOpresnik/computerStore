package com.example.computer_store

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.computer_store.databinding.ActivityAboutBinding

private var countLaunches = 0

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countLaunches++
        Log.i("times about page launched", countLaunches.toString())

        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val backButton = binding.backButton
        backButton.setOnClickListener {
            finish()
        }

        // displays app ID (UUID) that is generated every time the app is run
        val sharedPrefs: SharedPreferences = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        binding.appID.hint = sharedPrefs.getString("UUID", "/")

    }
}