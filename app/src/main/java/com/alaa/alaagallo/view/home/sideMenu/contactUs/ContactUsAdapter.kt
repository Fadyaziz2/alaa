package com.alaa.alaagallo.view.home.sideMenu.contactUs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R

class ContactUsAdapter(private val context: Context):
    RecyclerView.Adapter<ContactUsAdapter.ViewHolder>() {

    private var contactUsList = ArrayList<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contact_us,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contactUsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<String>){
        contactUsList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.phoneNumber.text = contactUsList[position]

        holder.whatsApp.setOnClickListener {
            openWhatsApp(contactUsList[position])
        }

        holder.call.setOnClickListener {
            callNumber(contactUsList[position])
        }
    }

    private fun callNumber(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(callIntent)
    }

    private fun openWhatsApp(phoneNumber: String) {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=+2$phoneNumber")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var phoneNumber = itemView.findViewById<TextView>(R.id.phone_number)
        var whatsApp = itemView.findViewById<ImageView>(R.id.whats_app)
        var call = itemView.findViewById<ImageView>(R.id.call)
    }
}