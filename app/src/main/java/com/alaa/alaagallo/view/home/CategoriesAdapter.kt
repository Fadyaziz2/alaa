package com.alaa.alaagallo.view.home

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
import com.alaa.alaagallo.model.home.Category
import com.squareup.picasso.Picasso

class CategoriesAdapter(private val callBack: AdapterToViewCallBack,
                        private val categoryCallback: IHome):
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    private var categoriesList = ArrayList<Category>()
    private val imagesList = arrayListOf<Int>()
    private val namesList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        setImages()
        setNames()
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = imagesList[position]
        Picasso.get()
            .load(imageUrl)
            .into(holder.categoryImage)

        holder.categoryName.text = namesList[position]

        holder.categoryItem.setOnClickListener {
            when (position) {
                0 -> {
                    categoryCallback.suggestPair()
                }
                1 -> {
                    categoryCallback.pairs()
                }
                2 -> {
                    categoryCallback.importantNumbers()
                }
                3 -> {
                    categoryCallback.searchCompa()
                }
                else -> {
                    // Handle other cases if necessary
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<Category>){
        categoriesList = newData
        notifyDataSetChanged()
    }

    private fun setImages () {
        //Set Images
        imagesList.add(R.drawable.suggest_img)
        imagesList.add(R.drawable.pair_img)
        imagesList.add(R.drawable.numbers_img)
        imagesList.add(R.drawable.my_accounts)
    }

    private fun setNames() {
        namesList.add("اقترح توافق")
        namesList.add("التوافقات")
        namesList.add("ارقام تهمك")
        namesList.add("حساباتي")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryImage = itemView.findViewById<ImageView>(R.id.img_category)
        var categoryName = itemView.findViewById<TextView>(R.id.txt_category_name)
        var categoryItem = itemView.findViewById<ConstraintLayout>(R.id.category_item)
    }

}