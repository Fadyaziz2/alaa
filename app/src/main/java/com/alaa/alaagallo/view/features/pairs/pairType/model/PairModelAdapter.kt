package com.alaa.alaagallo.view.features.pairs.pairType.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.modelss.ModelsResponse
import com.alaa.alaagallo.view.features.pairs.SelectedPairType
import com.alaa.alaagallo.view.home.IHome
import com.squareup.picasso.Picasso

class PairModelAdapter(private val modelCallBack: IHome): RecyclerView.Adapter<PairModelAdapter.ViewHolder>() {

    private var modelsList : List<com.alaa.alaagallo.model.showbrands.Model> = listOf()

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
    fun setData(newData: List<com.alaa.alaagallo.model.showbrands.Model>) {
        modelsList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(modelsList[position].image)
            .into(holder.modelImage)

        holder.modelName.text = modelsList[position].name
//        holder.modelDetails.text = modelsList[position].brand
        holder.modelItem.setOnClickListener {

            modelCallBack.sendSubCategoriesData(modelsList[position].name,modelsList[position].id.toString(),modelsList[position].image)
        }

//        holder.modelItem.setOnClickListener {
//            setModelData(modelsList[position],position)
//            modelCallBack.openActivity()
//        }
    }

    private fun setModelData(model: ModelsResponse,pos:Int) {
        SelectedPairType.modelId = model.data.get(pos).id.toString()
        SelectedPairType.modelImage = model.data.get(pos).image
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelImage: ImageView = itemView.findViewById(R.id.img_model)
        val modelName: TextView = itemView.findViewById(R.id.model_name)
        val modelDetails: TextView = itemView.findViewById(R.id.model_details)
        val modelItem: ConstraintLayout = itemView.findViewById(R.id.item_model)
    }
}