package com.example.computer_store

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.computer_store.databinding.ActivityDisplayBinding
import com.example.computer_store.databinding.ActivityMenuBinding
import com.example.computerutils.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.io.FileUtils.writeStringToFile
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.UUID


private var countLaunches = 0

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countLaunches++
        Log.i("times app launched", countLaunches.toString())

        app = application as MyApplication
        Log.i("data", app.data.listofComputers.size.toString())

        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // launchInputActivity
        val addButton = binding.addButton
        addButton.setOnClickListener {
            val inputActivity = Intent(this, InputActivity::class.java)
            // launch from results
            results.launch(inputActivity)
        }

        // launch aboutActivity
        val aboutButton = binding.aboutButton
        aboutButton.setOnClickListener {
            val aboutActivity = Intent(this, AboutActivity::class.java)
            startActivity(aboutActivity)
        }

        /*
        https://www.qr-code-generator.com/
        lenovo;legion;ryzen5;3.5;6;12;gtx1650;1600;1000;8;1;SSD;512;1920;1080;120;IPS;849.99    <--- format QR kode
         */
        val qrButton = binding.qrButton
        qrButton.setOnClickListener {
            try {
                // launch qr code scanner
                val scanner = Intent("com.google.zxing.client.android.SCAN")
                scanner.putExtra("SCAN_MODE", "QR_CODE_MODE")
                qrResults.launch(scanner)
            } catch (e: Exception) {
                val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                startActivity(marketIntent)
            }
        }

        val settingsButton = binding.settingsButton
        settingsButton.setOnClickListener {
            val settingsActivity = Intent(this, SettingsActivity::class.java)
            startActivity(settingsActivity)
        }


        // exit app
        val exitButton = binding.exitButton
        exitButton.setOnClickListener {
            finish()
        }

        val displayButton = binding.displayButton
        displayButton.setOnClickListener {
            app.data = generateComputers(10)
            app.saveToFile()
            val displayActivity = Intent(this, DisplayActivity::class.java)
            startActivity(displayActivity)
        }


    }



    // handling data sent from input activity
    private val results = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            //val receivedId = intent?.getStringExtra("id") as String
            val receivedBrand = intent?.getStringExtra("brand") as String
            val receivedModel = intent.getStringExtra("model") as String
            val receivedCpuBrand = intent.getStringExtra("cpu_brand") as String
            val receivedCpuSpeed = intent.getDoubleExtra("cpu_speed", 0.0)
            val receivedCpuCores = intent.getIntExtra("cpu_cores", 0)
            val receivedCpuThreads = intent.getIntExtra("cpu_threads", 0)
            val receivedGpuBrand = intent.getStringExtra("gpu_brand") as String
            val receivedGpuSpeed = intent.getIntExtra("gpu_speed", 0)
            val receivedGpuCores = intent.getIntExtra("gpu_cores", 0)
            val receivedRamCapacity = intent.getIntExtra("ram_capacity", 0)
            val receivedRamSticks = intent.getIntExtra("ram_sticks", 0)
            val receivedDiskType = intent.getStringExtra("disk_type") as String
            val receivedDiskCapacity = intent.getIntExtra("disk_capacity", 0)
            val receviedScreenWidth = intent.getIntExtra("screen_width", 0)
            val receivedScreenHeight = intent.getIntExtra("screen_height", 0)
            val receivedScreenRefresh = intent.getIntExtra("screen_refresh", 0)
            val receivedScreenPanel = intent.getStringExtra("screen_panel") as String
            val receivedPrice = intent.getDoubleExtra("price", 0.0)

            val cpu = Processor(receivedCpuBrand, receivedCpuSpeed, receivedCpuCores, receivedCpuThreads)
            val gpu = GraphicsCard(receivedGpuBrand, receivedGpuSpeed, receivedGpuCores)
            val ram = Ram(receivedRamCapacity, receivedRamSticks)
            val storage = Storage(StorageType.valueOf(receivedDiskType), receivedDiskCapacity)
            val screen = Screen(receviedScreenWidth, receivedScreenHeight, receivedScreenRefresh, PanelType.valueOf(receivedScreenPanel))

            // creating new computer object
            val computer = Computer(receivedBrand, receivedModel, screen, cpu, gpu, ram, storage, receivedPrice)

            // adding computer to the list
            //app.data += computer
            app.data.addComputer(computer)

            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.toJson(computer)
            Log.i("computerJSON", json)
            Log.i("data", app.data.toString())
            Log.i("number of computers", app.data.listofComputers.size.toString())
            // save new computer to file (mydata.json)
            app.saveToFile()

            /*for (comp in app.data) {
                Log.i("computer", comp.getUUID())
                //val file = File(filesDir, "data.json")
                val json = gson.toJson(comp)
                File("data.json").writeText(json)
                //val json = writeStringToFile(file, gson.toJson(computers))
                //val json = gson.toJson(comp, FileWriter("Desktop\\data.txt"))
                Log.i("computerJSON", json)
            }*/

            Toast.makeText(this, "computer added successfully", Toast.LENGTH_SHORT).show()
            Log.i("computer added", "success")
        }
    }

    // handling data scanned from QR code
    private val qrResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val data = intent?.getStringExtra("SCAN_RESULT")

            if (data != null) {
                try {
                    val details = data.split(";")
                    //val id = UUID.randomUUID().toString()
                    val brand = details[0]
                    val model = details[1]
                    val cpu = Processor(details[2], details[3].toDouble(), details[4].toInt(), details[5].toInt())
                    val gpu = GraphicsCard(details[6], details[7].toInt(), details[8].toInt())
                    val ram = Ram(details[9].toInt(), details[10].toInt())
                    val storage = Storage(StorageType.valueOf(details[11]), details[12].toInt())
                    val screen = Screen(details[13].toInt(), details[14].toInt(), details[15].toInt(), PanelType.valueOf(details[16]))
                    val price = details[17].toDouble()
                    val computer = Computer(brand, model, screen, cpu, gpu, ram, storage, price)

                    //app.data += computer
                    app.data.addComputer(computer)

                    val gson: Gson = GsonBuilder().setPrettyPrinting().create()
                    val json = gson.toJson(computer)
                    Log.i("computerJSON", json)
                    Log.i("data", app.data.toString())
                    // save new computer to file (mydata.json)
                    app.saveToFile()

                    Toast.makeText(this, "computer added successfully", Toast.LENGTH_SHORT).show()
                    Log.i("computer added via QR code", "success")
                } catch (e: Exception) {
                    Toast.makeText(this, "incorrect data format", Toast.LENGTH_SHORT).show()
                    Log.i("qr code data format", "incorrect")
                }
            }
            else {
                Toast.makeText(this, "no data provided", Toast.LENGTH_SHORT).show()
                Log.i("qr code data", "missing")
            }
        }
        else {
            Toast.makeText(this, "no data received", Toast.LENGTH_SHORT).show()
            Log.i("received data", "missing")
        }
    }
}