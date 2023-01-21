package com.example.computer_store

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.computerutils.Computer
import com.example.computerutils.ComputersList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.writeStringToFile
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.UUID


const val FILE_NAME = "mydata.json"
const val SHARED_DATA = "myshared.data"

class MyApplication : Application() {
    var data: ComputersList = ComputersList()
    private var gson: Gson = GsonBuilder().setPrettyPrinting().create()
    lateinit var file: File

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        gson = Gson()
        file = File(filesDir, FILE_NAME)
        Log.i("filename", file.path)

        //saveToFile()
        readFromFile()

        initSharedPrefs()

        if (!containsUUID()) {
            saveUUID(UUID.randomUUID().toString())
        }
        Timber.d("app UUID ${getUUID()}")

    }


    private fun initSharedPrefs() {
        sharedPrefs = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
    }

    private fun saveUUID(data: String) {
        with(sharedPrefs.edit()) {
            putString("UUID", data)
            // commit preferences changes
            apply()
        }
    }

    private fun getUUID(): String? {
        return sharedPrefs.getString("UUID", "default_data")
    }

    private fun containsUUID(): Boolean {
        return sharedPrefs.contains("UUID")
    }

    fun saveToFile() {
        try {
            writeStringToFile(file, gson.toJson(data))
            Log.i("saving", "saved to file")
        } catch (e: IOException) {
            Timber.d("unable to save " + file.path)
        }
    }

    private fun readFromFile() {
        data = try {
            Log.i("reading", "reading from file")
            gson.fromJson(FileUtils.readFileToString(file), ComputersList::class.java)
        } catch (e: IOException) {
            Timber.d("no data in file")
            this.data
        }
    }

    fun saveThemeMode(themeMode: String) {
        with(sharedPrefs.edit()) {
            putString("themeMode", themeMode)
            apply()
        }
    }

    fun saveSelectedCurrency(currency: String) {
        with(sharedPrefs.edit()) {
            putString("currency", currency)
            apply()
        }
    }

}