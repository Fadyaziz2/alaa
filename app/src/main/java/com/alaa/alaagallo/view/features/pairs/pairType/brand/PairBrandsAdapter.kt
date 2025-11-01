package com.alaa.alaagallo.view.features.pairs.pairType.brand

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
import com.alaa.alaagallo.model.brands.BrandsResponse
import com.alaa.alaagallo.model.brands.DataBrand
import com.alaa.alaagallo.view.features.pairs.SelectedPairType
import com.alaa.alaagallo.view.home.IHome
import com.squareup.picasso.Picasso

class PairBrandsAdapter(private val brandsCallBack: IHome): RecyclerView.Adapter<PairBrandsAdapter.ViewHolder>() {

    private var brandsList :List<DataBrand> =listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                //R.layout.item_brand,
                R.layout.item_brand_update,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        Log.d("size", brandsList.size.toString())

        return brandsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.brandName.text = brandsList[position].name
        Picasso.get()
            .load(brandsList[position].image)
            .into(holder.brandImage)

        holder.brandItem.setOnClickListener {
//            setBrandData(brandsList[position])
            brandsCallBack.sendSubCategoriesData(brandsList[position].name, brandsList[position].id.toString(),brandsList[position].image)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<DataBrand>){
        brandsList = newData
        notifyDataSetChanged()
    }

    private fun setBrandData(brand: BrandsResponse,position:Int) {
        SelectedPairType.brandImage = brand.data.get(position).image
        SelectedPairType.brandId = brand.data.get(position).id.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandName = itemView.findViewById<TextView>(R.id.brand_update_name)
        val brandImage = itemView.findViewById<ImageView>(R.id.brand_update_image)
        val brandItem = itemView.findViewById<ConstraintLayout>(R.id.item_brand)
        /*
        val brandName = itemView.findViewById<TextView>(R.id.brand_name)
        val brandImage = itemView.findViewById<ImageView>(R.id.brand_image)
        val brandItem = itemView.findViewById<ConstraintLayout>(R.id.item_brand)

         */
    }
}