package com.alaa.alaagallo.view.home.search_comp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.view.features.pairs.pairType.interaction.SearchInteraction
import com.alaa.alaagallo.view.home.search_comp.newsearchresponse.SearchRespoooo
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class SearchAdapter(private val interaction: SearchInteraction) :
    PagingDataAdapter<SearchRespoooo, SearchAdapter.ViewHolder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<SearchRespoooo>() {
        override fun areItemsTheSame(oldItem: SearchRespoooo, newItem: SearchRespoooo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchRespoooo, newItem: SearchRespoooo): Boolean {
            return oldItem == newItem
        }

    }
//    private var pairResultList: List<SearchRespoooo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pair_result,
                parent,
                false
            )
        )
    }

//    override fun getItemCount(): Int {
//        return pairResultList.size
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.pairResultBrand.text = getItem(position)?.compatibilities_brand
        holder.pairResultModelName.text = getItem(position)?.compatibilities_mod
//            holder.pairResultModelDetails.text = pairResultList[position].description.toString()
//        }

        getItem(position)?.description?.let {
//            val arabicRegex = Regex("[\\u0600-\\u06FF]+")
//            val englishRegex = Regex("[A-Za-z]+")
            val document: Document = Jsoup.parse(getItem(position)?.description!!)
            val imgElement = document.select("img").first()
            val imgUrl = imgElement?.attr("src")
            val text = document.text().takeIf { it.isNotEmpty() } ?: "No description available"
            if (text == "No description available")
                holder.pairResultDescriptionText.visibility = View.GONE
            else {
                holder.pairResultDescriptionText.visibility = View.VISIBLE
                holder.pairResultDescriptionText.text = text
            }
            if (imgUrl != null) {
//                when{
//                    arabicRegex.containsMatchIn(text) -> holder.pairResultDescriptionImage.text = "عرض المزيد"
//                    englishRegex.containsMatchIn(text) -> holder.pairResultDescriptionImage.text = "see more"
//                    else -> holder.pairResultDescriptionImage.text = "see more"
//                }
                holder.pairResultDescriptionImage.text = "عرض المزيد"
                holder.pairResultDescriptionImage.visibility = View.VISIBLE
            } else {
                holder.pairResultDescriptionImage.visibility = View.GONE
            }
            holder.pairResultDescriptionImage.setOnClickListener {
                interaction.onClickShowImage(
                    getItem(position)?.description.toString()
                )
            }
        }
        Picasso.get()
            .load(getItem(position)?.compatibilities_mod_image)
            .into(holder.pairResultImage)
        holder.pairResultImage.setOnClickListener {
            interaction.onClickImage(getItem(position)?.compatibilities_mod_image!!)
        }
        // IDK what should i  do here ?

//        if (pairResultList[position].pairResultConfirmation) {
//            holder.pairResultConfirmation.setText(R.string.confirmed_pair)
//            holder.pairResultConfirmationLayout.setBackgroundResource(R.drawable.confirmed_pair_bg)
//        }else{
//            holder.pairResultConfirmation.setText(R.string.not_confirmed_pair)
//            holder.pairResultConfirmationLayout.setBackgroundResource(R.drawable.not_confirmed_pair_bg)
//        }
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(newData: List<SearchRespoooo>) {
//        pairResultList = newData
//        notifyDataSetChanged()
//    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pairResultBrand = itemView.findViewById<TextView>(R.id.pair_result_brand)
        var pairResultImage = itemView.findViewById<ImageView>(R.id.pair_result_image)
        var pairResultModelName = itemView.findViewById<TextView>(R.id.pair_result_model_name)
        var pairResultDescriptionText =
            itemView.findViewById<TextView>(R.id.pair_result_description)
        var pairResultDescriptionImage =
            itemView.findViewById<TextView>(R.id.pair_result_description_image)
    }
}