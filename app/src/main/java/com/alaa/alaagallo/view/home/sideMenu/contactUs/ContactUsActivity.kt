package com.alaa.alaagallo.view.home.sideMenu.contactUs

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R

class ContactUsActivity : ComponentActivity() {

    private val back : ImageView get() = findViewById(R.id.iv_back)
    private val contactUsRecyclerView: RecyclerView get() = findViewById(R.id.contact_us_recycler_view)
    private val contactUsAdapter = ContactUsAdapter(this)
    private val contactUsList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_us)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        setDummyData()

    }

    private fun setDummyData() {
        contactUsList.add("01030800011")
        contactUsList.add("01095202426")
        contactUsList.add("01017771040")
        /*
        for (i in 0..2) {
            contactUsList.add(ContactUs)
        }

         */
        contactUsAdapter.setData(contactUsList)
    }

    private fun initView() {
        //Initialize recycler view
        contactUsRecyclerView.layoutManager = LinearLayoutManager(this)
        contactUsRecyclerView.adapter = contactUsAdapter

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}