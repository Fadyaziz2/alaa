package com.alaa.alaagallo.view.features.pairs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.AdapterToViewCallBack
import com.alaa.alaagallo.model.categroies.CategoriesModelResponse
import com.alaa.alaagallo.view.home.IHome

class PairsAdapter(private val callBack: AdapterToViewCallBack,
                   private val pairCallCallback: IHome
): RecyclerView.Adapter<PairsAdapter.ViewHolder>() {

    private var pairsList : List<CategoriesModelResponse> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pair,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pairName.text = pairsList[position].data.get(position).name
//        Picasso.get()
//            .load(pairsList[position].data.get(position).)
//            .placeholder(R.drawable.img)
//            .into(holder.pairImage)

        holder.pairItem.setOnClickListener {
//            setSelectedPairData(pairsList[position],position)
//            pairCallCallback.openActivity()
            pairCallCallback.sendSubCategoriesData(pairsList[position].data.get(position).name.toString(),
                pairsList[position].data.get(position).id.toString(),""
            )
        }
    }

    private fun setSelectedPairData(pair: CategoriesModelResponse,position: Int) {
        SelectedPairType.pairId = pair.data.get(position).id.toString()
        SelectedPairType.pairName = pair.data.get(position).name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CategoriesModelResponse>) {
        pairsList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return pairsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pairImage = itemView.findViewById<ImageView>(R.id.pair_image)
        val pairName = itemView.findViewById<TextView>(R.id.pair_name)
        val pairItem = itemView.findViewById<ConstraintLayout>(R.id.item_pair)
    }
}