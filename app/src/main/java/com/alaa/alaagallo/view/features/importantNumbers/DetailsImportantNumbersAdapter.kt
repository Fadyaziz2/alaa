package com.alaa.alaagallo.view.features.importantNumbers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.importantNumber.ImportantNumber
import com.alaa.alaagallo.model.newnumbers.NumberX
import com.alaa.alaagallo.view.home.NumbersInterface

class DetailsImportantNumbersAdapter(private val importantNumberCallCallback: NumbersInterface) : RecyclerView.Adapter<DetailsImportantNumbersAdapter.ViewHolder>(){

    private var importantNumbersList :List<NumberX> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.details_important_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.importantNumberName.text = importantNumbersList[position].name
        holder.importantNumberItem.text = importantNumbersList[position].number
//if(importantNumbersList[position].image!=null && !importantNumbersList[position].image.isNullOrEmpty()) {
//    Picasso.get()
//        .load(importantNumbersList[position].image)
//        .into(holder.importantNumberImage)
//}
        holder.itemView.setOnClickListener {
           importantNumberCallCallback.sendNumberToCall(importantNumbersList[position])
        }

//
//        holder.importantNumberItem.setOnClickListener {
//            val context = it.context
//            val phoneNumber = importantNumbersList[position].number
//
//                val callIntent = Intent(Intent.ACTION_CALL)
//                callIntent.data = Uri.parse("tel:$phoneNumber")
//                context.startActivity(callIntent)
//
//
//        }
    }

    private fun setSelectedNumber(importantNumber: ImportantNumber) {
        SelectedNumber.numberId = importantNumber.importantNumberId
        SelectedNumber.numberName = importantNumber.importantNumberName
    }

    override fun getItemCount(): Int {
        return  importantNumbersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<NumberX>) {
        importantNumbersList = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val importantNumberImage = itemView.findViewById<ImageView>(R.id.important_number_image)
        val importantNumberName = itemView.findViewById<TextView>(R.id.textDtails)
        val importantNumberItem = itemView.findViewById<TextView>(R.id.textDtails_numbers)
    }
}