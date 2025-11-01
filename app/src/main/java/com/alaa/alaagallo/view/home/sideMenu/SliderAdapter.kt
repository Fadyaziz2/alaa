package com.alaa.alaagallo.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R

class SliderAdapter(private val images: List<Int>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(images[position], position)
    }

    override fun getItemCount(): Int = images.size

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        //private val indOne = v.findViewById<View>(R.id.home_slider_tv_indicator_one)
        //private val indTwo = v.findViewById<View>(R.id.home_slider_tv_indicator_two)

        @SuppressLint("ResourceAsColor")
        fun bind(imageRes: Int, position: Int) {
            imageView.setImageResource(imageRes)
        }
    }
}
