package com.example.computer_store

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.computer_store.databinding.ActivityDisplayBinding
import com.example.computerutils.*

class DisplayActivity : AppCompatActivity() {

    //private lateinit var list: ArrayList<Computer>
    private var list = mutableListOf<Computer>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var app: MyApplication

    private lateinit var binding: ActivityDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("filepath", app.file.path)
        Log.i("computers", app.data.listofComputers.size.toString())
        Log.i("comp1", app.data.toString())


        val startResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data

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
                val receivedIndex = intent.getIntExtra("index", 0)

                Log.i("received index", receivedIndex.toString())

                val cpu = Processor(receivedCpuBrand, receivedCpuSpeed, receivedCpuCores, receivedCpuThreads)
                val gpu = GraphicsCard(receivedGpuBrand, receivedGpuSpeed, receivedGpuCores)
                val ram = Ram(receivedRamCapacity, receivedRamSticks)
                val storage = Storage(StorageType.valueOf(receivedDiskType), receivedDiskCapacity)
                val screen = Screen(receviedScreenWidth, receivedScreenHeight, receivedScreenRefresh, PanelType.valueOf(receivedScreenPanel))

                // creating new computer object
                val computer = Computer(receivedBrand, receivedModel, screen, cpu, gpu, ram, storage, receivedPrice)

                app.data.updateAtIndex(receivedIndex, computer)
                adapter.notifyItemChanged(receivedIndex)
                app.saveToFile()
                Toast.makeText(applicationContext, "Computer list updated successfully", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.i("error", "returned with no value (${result}")
            }
        }

        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = RecyclerAdapter(app.data, object: RecyclerAdapter.myOnClick {
                override fun onClick(p0: View?, position: Int) {
                    Log.i("short click", "here code comes at ${position}")
                    val intent = Intent(applicationContext, InputActivity::class.java)
                    intent.putExtra("index", position)
                    //setResult(Activity.RESULT_OK, intent)
                    Log.i("pos", position.toString())
                    startResult.launch(intent)
                }}, object: RecyclerAdapter.myOnLongClick {
                override fun onLongClick(p0: View?, position: Int) {
                    Log.i("long click", "here code comes at ${position}")
                    val builder = AlertDialog.Builder(this@DisplayActivity)
                    builder.setTitle("DELETE")
                    builder.setMessage("Are you sure you want to delete this item")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes") { dialogInterface, which ->
                        Toast.makeText(applicationContext, "Deleting item", Toast.LENGTH_LONG).show()
                        app.data.listofComputers.removeAt(position)
                        adapter.notifyDataSetChanged()
                        app.saveToFile()
                    }
                    builder.setNeutralButton("Cancel") { dialogInterface, which ->
                        Toast.makeText(applicationContext, "Deletion cancelled", Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("No") { dialogInterface, which ->
                        Toast.makeText(applicationContext, "Deletion cancelled", Toast.LENGTH_LONG).show()
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            })
            binding.recyclerView.adapter = adapter
        } catch (err: Error) {
            Log.i("error", "error regarding dialog box (${err}")
        }





        //list = ArrayList()
        //recyclerView = findViewById(R.id.recyclerView)
        //setComputerInfo()
        //setAdapter()
    }

    fun addComputerToList(resultData: ActivityResult) {

    }

    /*private fun setAdapter() {
        val adapter = RecyclerAdapter(app.data)
        val layoutmanager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutmanager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    private fun setComputerInfo() {
        Log.i("computers num", app.data.size.toString())
        Log.i("comp1", app.data.toString())
        //list.clear()
        for (computer in app.data) {
            val brand = computer.getBrand()
            val model = computer.getModel()
            Log.i("comp", brand)
            list.add(computer)
        }

        /*list.add("Lenovo")
        list.add("Razer")
        list.add("ROG")*/
    }*/

    fun back(view: View) {
        finish()
    }
}