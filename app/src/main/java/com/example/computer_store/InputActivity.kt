package com.example.computer_store

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.computer_store.databinding.ActivityInputBinding
import com.example.computerutils.*  // import my module
import java.util.UUID


private var countLaunches = 0

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setting up view binding
        binding = ActivityInputBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        countLaunches++
        Log.i("times settings launched", countLaunches.toString())

        // generate computers
        //val computers = generateComputers(3)

        // sort by price
        //val sorted = sort(computers)

        // print details
        /*var counter = 1
        for (c in sorted) {
            Log.i("computer$counter", c.toString())
            counter++
        }*/

        // set currency in the input field based on what is saved in the shared preferences
        val sharedPrefs: SharedPreferences = getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        binding.priceInput.hint = sharedPrefs.getString("currency", "")

    }

    fun back(view: View) {
        finish()
    }

    /**
     * extracts text from input fields
     * and creates Computer object based on the inputs
     * adds created computer to a list of computers
     */
    @SuppressLint("WrongViewCast")
    fun addComputer(view: View) {

        val inputs = mutableListOf<EditText>()

        val input1 = binding.brandInput
        inputs += input1

        val input2 = binding.procesorInputBrand
        inputs += input2

        val input3 = binding.procesorInputSpeed
        inputs += input3

        val input4 = binding.procesorInputCores
        inputs += input4

        val input5 = binding.procesorInputThreads
        inputs += input5

        val input6 = binding.graficnaInputBrand
        inputs += input6

        val input7 = binding.graficnaInputSpeed
        inputs += input7

        val input8 = binding.graficnaInputCores
        inputs += input8

        val input9 = binding.ramInputCapacity
        inputs += input9

        val input10 = binding.ramInputSticks
        inputs += input10

        val input11 = binding.diskInputType
        inputs += input11

        val input12 = binding.diskInputCapacity
        inputs += input12

        val input13 = binding.screenInputWidth
        inputs += input13

        val input14 = binding.screenInputHeight
        inputs += input14

        val input15 = binding.screenInputRefreshRate
        inputs += input15

        val input16 = binding.screenInputPanel
        inputs += input16

        val input17 = binding.priceInput
        inputs += input17

        for (i in inputs) {
            Log.i("input field content", i.text.toString())
        }


        var empty = false
        for (i in inputs) {
            // display a toast if at least one input field is empty
            if (i.text.toString().isEmpty()) {
                val toast = Toast.makeText(applicationContext, "you left some input(s) empty", Toast.LENGTH_SHORT)
                toast.show()
                empty = true
            }
            if (empty) break
        }

        Log.i("empty", empty.toString())

        // if none are empty, create all necessary objects
        if (!empty) {
            for (i in inputs) {
                Log.i("input field content", i.text.toString())
            }

            //val id = UUID.randomUUID().toString()

            val name = input1.text.toString()
            val brand = name.split(" ")[0]
            val model = name.split(" ")[1]

            val cpuBrand = input2.text.toString()
            val cpuSpeed = input3.text.toString().toDouble()
            val cpuCores = input4.text.toString().toInt()
            val cpuThreads = input5.text.toString().toInt()

            val gpuBrand = input6.text.toString()
            val gpuSpeed = input7.text.toString().toInt()
            val gpuCores = input8.text.toString().toInt()

            val ramCapacity = input9.text.toString().toInt()
            val ramSticks = input10.text.toString().toInt()

            val diskType = input11.text.toString()
            val diskCapacity = input12.text.toString().toInt()

            val screenWidth = input13.text.toString().toInt()
            val screenHeight = input14.text.toString().toInt()
            val screenRefresh = input15.text.toString().toInt()
            val screenPanel = input16.text.toString()

            val price = input17.text.toString().toDouble()

            /*val cpu = Processor(cpuBrand, cpuSpeed, cpuCores, cpuThreads)
            val gpu = GraphicsCard(gpuBrand, gpuSpeed, gpuCores)
            val ram = Ram(ramCapacity, ramSticks)
            val disk = Storage(StorageType.valueOf(diskType), diskCapacity)
            val screen = Screen(screenWidth, screenHeight, screenRefresh, PanelType.valueOf(screenPanel))

            val computer = Computer(brand, model, screen, cpu, gpu, ram, disk, price)

            // add computer to the list
            computers += computer*/
            //Log.i("computer added: ", "success")

            // clear all input fields
            for (i in inputs) {
                i.text.clear()
            }

            // prepare data to be sent
            val data = Intent()

            //data.putExtra("id", id)
            data.putExtra("brand", brand)
            data.putExtra("model", model)
            data.putExtra("cpu_brand", cpuBrand)
            data.putExtra("cpu_speed", cpuSpeed)
            data.putExtra("cpu_cores", cpuCores)
            data.putExtra("cpu_threads", cpuThreads)
            data.putExtra("gpu_brand", gpuBrand)
            data.putExtra("gpu_speed", gpuSpeed)
            data.putExtra("gpu_cores", gpuCores)
            data.putExtra("ram_capacity", ramCapacity)
            data.putExtra("ram_sticks", ramSticks)
            data.putExtra("disk_type", diskType)
            data.putExtra("disk_capacity", diskCapacity)
            data.putExtra("screen_width", screenWidth)
            data.putExtra("screen_height", screenHeight)
            data.putExtra("screen_refresh", screenRefresh)
            data.putExtra("screen_panel", screenPanel)
            data.putExtra("price", price)

            val index = intent.getIntExtra("index", -1)
            if (index > -1) {
                data.putExtra("index", index)
            }

            setResult(Activity.RESULT_OK, data)

            Log.i("data ready to be sent", "success")

            // exit activity
            finish()
        }
        // handling when at least one input is empty
        else {
            Log.i("data ready to be sent", "failed (not enough data provided)")
        }
    }
}