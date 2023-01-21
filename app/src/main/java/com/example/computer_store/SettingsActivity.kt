package com.example.computer_store

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.example.computer_store.databinding.ActivitySettingsBinding

private var countLaunches = 0

open class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivitySettingsBinding

    lateinit var app: MyApplication
    private lateinit var sharedPrefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        countLaunches++
        Log.i("times settings launched", countLaunches.toString())

        app = application as MyApplication
        sharedPrefs = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)

        // change app theme
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.themeSwitch.isChecked = true
        }
        binding.themeSwitch.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                app.saveThemeMode("light")
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                app.saveThemeMode("dark")
            }
        }

        //Log.i("currency", sharedPrefs.getString("currency", "").toString())
        app.saveSelectedCurrency(sharedPrefs.getString("currency", "").toString())

        binding.backButton.setOnClickListener {
            finish()
        }

        // setting up dropdown menu (spinner)
        val currencySpinner = binding.currencySpinner
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        currencySpinner.adapter = adapter

        currencySpinner.onItemSelectedListener = this
        Log.i("currency", sharedPrefs.getString("currency", "").toString())

    }

    // save selected item from dropdown menu to shared preferences
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //sharedPrefs = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            val selected = parent?.getItemAtPosition(position) as String?
            putString("currency", selected)
            Log.i("selected currency", selected!!)
            apply()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        sharedPrefs = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            val selected = ""
            putString("currency", selected)
            Log.i("selected currency", "nothing selected")
            apply()
        }
    }
}