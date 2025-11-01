package com.alaa.alaagallo.view.features.importantNumbers.details_important_number

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.model.newnumbers.Number

    class CustomSpinnerAdapter (context: Context, private val items: MutableList<Number>) :
        ArrayAdapter<Number>(context, R.layout.spinner_item, items) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createItemView(position, convertView, parent)
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createItemView(position, convertView, parent)
        }

        private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
            val item = getItem(position)
            val textView = view.findViewById<TextView>(R.id.spinner_item_text)
            textView.text = item?.name
            return view
        }
    }