package com.alaa.alaagallo.view.features.suggestPair.SuggestType

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.typess.Data
import com.alaa.alaagallo.view.home.IHome
import com.squareup.picasso.Picasso

class SuggestTypeAdapter( private val pairCallCallback: IHome): RecyclerView.Adapter<SuggestTypeAdapter.ViewHolder>() {

    private var typesList: List<Data> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pair,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return typesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = typesList[position]
        holder.typeName.text = dataItem.name

        if (dataItem!= null) {
            Picasso.get()
                .load(dataItem.image) // Assuming you want the first type's media
                .placeholder(R.drawable.img)
                .into(holder.typeImage)
        }

        holder.typeItem.setOnClickListener {
            Log.d("testIDTypeAda",dataItem.id.toString());
            pairCallCallback.sendSubCategoriesData(dataItem.name, dataItem.id.toString(),"")
        }
    }

//    private fun setSelectedPairData(type: CategoriesModelResponse) {
//        Suggestion.typeId = type.typeId
//        Suggestion.typeImage = type.typeImageUrl
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Data>) {
        typesList = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeImage = itemView.findViewById<ImageView>(R.id.pair_image)
        val typeName = itemView.findViewById<TextView>(R.id.pair_name)
        val typeItem = itemView.findViewById<ConstraintLayout>(R.id.item_pair)
    }
}