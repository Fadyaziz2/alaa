package com.alaa.alaagallo.view.features.suggestPair.SuggestModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.modelss.DataModels
import com.alaa.alaagallo.view.home.IHome
import com.squareup.picasso.Picasso

class SuggestModelAdapter(private val modelCallBack: IHome): RecyclerView.Adapter<SuggestModelAdapter.ViewHolder>() {

    private var modelsList:List<DataModels> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_model,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return modelsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<DataModels>) {
        modelsList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(modelsList[position].image)
            .into(holder.modelImage)

        holder.modelName.text = modelsList[position].name
        holder.modelDetails.text = modelsList[position].brand

        holder.modelItem.setOnClickListener {
//            setModelData(modelsList[position])
            modelCallBack .sendSubCategoriesData(modelsList[position].name,
                modelsList[position].id.toString(),"")
        }
    }

//    private fun setModelData(model: Model) {
//        Suggestion.modelId = model.modelId
//        Suggestion.modelImage = model.modelImage
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelImage: ImageView = itemView.findViewById(R.id.img_model)
        val modelName: TextView = itemView.findViewById(R.id.model_name)
        val modelDetails: TextView = itemView.findViewById(R.id.model_details)
        val modelItem: ConstraintLayout = itemView.findViewById(R.id.item_model)
    }
}