package com.alaa.alaagallo.view.features.importantNumbers.numberDetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.importantNumber.NumberService

class NumberServicesAdapter: RecyclerView.Adapter<NumberServicesAdapter.ViewHolder>() {

    private var numberServicesList = ArrayList<NumberService>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_number_service,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.serviceNumber.text = numberServicesList[position].serviceNumber
        holder.serviceName.text = numberServicesList[position].serviceName
    }

    override fun getItemCount(): Int {
        return numberServicesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<NumberService>) {
        numberServicesList = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceItem = itemView.findViewById<ConstraintLayout>(R.id.item_number_service)
        val serviceName = itemView.findViewById<TextView>(R.id.tv_service_name)
        val serviceNumber = itemView.findViewById<TextView>(R.id.tv_service_number)
    }
}