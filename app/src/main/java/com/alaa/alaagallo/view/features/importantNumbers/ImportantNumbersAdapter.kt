package com.alaa.alaagallo.view.features.importantNumbers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.importantNumber.ImportantNumber
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.home.IHome
import com.squareup.picasso.Picasso

class ImportantNumbersAdapter(private val importantNumberCallCallback: IHome) : RecyclerView.Adapter<ImportantNumbersAdapter.ViewHolder>(){

    private var importantNumbersList :List<Data> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_important_number,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.importantNumberName.text = importantNumbersList[position].name
if(importantNumbersList[position].image!=null && !importantNumbersList[position].image.isNullOrEmpty()) {
    Picasso.get()
        .load(importantNumbersList[position].image)
        .into(holder.importantNumberImage)
}
        holder.importantNumberItem.setOnClickListener {
           importantNumberCallCallback.sendNumbersToDetailsNumbers(importantNumbersList[position])
        }
    }

    private fun setSelectedNumber(importantNumber: ImportantNumber) {
        SelectedNumber.numberId = importantNumber.importantNumberId
        SelectedNumber.numberName = importantNumber.importantNumberName
    }

    override fun getItemCount(): Int {
        return  importantNumbersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Data>) {
        importantNumbersList = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val importantNumberImage = itemView.findViewById<ImageView>(R.id.important_number_image)
        val importantNumberName = itemView.findViewById<TextView>(R.id.important_number_name)
        val importantNumberItem = itemView.findViewById<ConstraintLayout>(R.id.item_important_number)
    }
}