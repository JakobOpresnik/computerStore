package com.example.computer_store

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.computerutils.Computer
import com.example.computerutils.ComputersList


class RecyclerAdapter(private val dataSet: ComputersList, private val onClickObject: myOnClick, private val onLongClickObject: myOnLongClick) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface myOnClick {
        fun onClick(p0: View?, position: Int)
    }

    interface myOnLongClick {
        fun onLongClick(p0: View?, position: Int)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val info: TextView = view.findViewById(R.id.textView)
        //val model: TextView = view.findViewById(R.id.textView2)
        //val price: TextView = view.findViewById(R.id.textView3)
        val line: CardView = view.findViewById(R.id.cvLine)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.computers_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("pos", position.toString())
        Log.i("dataSet", dataSet.toString())
        //Log.i("dataSet[1]", dataSet.listofComputers[1].toString())
        val itemsViewModel: Computer = dataSet.listofComputers[position]

        //holder.brand.text = itemsViewModel.getBrand()
        //holder.model.text = itemsViewModel.getModel()
        //holder.price.text = itemsViewModel.getPrice().toString()

        holder.info.text = itemsViewModel.toString()

        holder.line.setOnClickListener { p0 ->
            Log.i("short click", "here code comes click on ${holder.adapterPosition}")
            onClickObject.onClick(p0, holder.adapterPosition)
        }

        holder.line.setOnLongClickListener { p0 ->
            //holder.line.setCardBackgroundColor(Color.RED)
            onLongClickObject.onLongClick(p0, holder.adapterPosition)
            true
        }


        /*val brand = dataSet[position].getBrand()
        val model = dataSet[position].getModel()
        holder.getBrand().text = brand
        holder.getModel().text = model*/
        //holder.getBrand().text = dataSet[position]
    }

    override fun getItemCount() = dataSet.listofComputers.size
}