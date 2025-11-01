package com.alaa.alaagallo.view.features.pairs.pairType

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.show_compaitibilites.Compatibilite

class PairResultAdapter: RecyclerView.Adapter<PairResultAdapter.ViewHolder>() {

    private var pairResultList :  List<Compatibilite> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pair_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return pairResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pairResultBrand.text = pairResultList[position].type
        holder.pairResultModelName.text = pairResultList[position].type
        holder.pairResultModelDetails.text = pairResultList[position].description

//        Picasso.get()
//            .load(pairResultList[position].data.compatibilites.get(position).)
//            .into(holder.pairResultImage)

        // IDK what should i  do here ?

//        if (pairResultList[position].pairResultConfirmation) {
//            holder.pairResultConfirmation.setText(R.string.confirmed_pair)
//            holder.pairResultConfirmationLayout.setBackgroundResource(R.drawable.confirmed_pair_bg)
//        }else{
//            holder.pairResultConfirmation.setText(R.string.not_confirmed_pair)
//            holder.pairResultConfirmationLayout.setBackgroundResource(R.drawable.not_confirmed_pair_bg)
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Compatibilite>) {
        pairResultList = newData
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pairResultBrand = itemView.findViewById<TextView>(R.id.pair_result_brand)
        var pairResultConfirmation = itemView.findViewById<TextView>(R.id.pair_result_confirmation)
        var pairResultConfirmationLayout = itemView.findViewById<LinearLayout>(R.id.pair_result_confirmation_layout)
        var pairResultImage = itemView.findViewById<ImageView>(R.id.pair_result_image)
        var pairResultModelName = itemView.findViewById<TextView>(R.id.pair_result_model_name)
        var pairResultModelDetails = itemView.findViewById<TextView>(R.id.pair_result_model_details)
    }
}