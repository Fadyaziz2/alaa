package com.alaa.alaagallo.view.features.importantNumbers.numberDetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.importantNumber.NumberService
import com.alaa.alaagallo.view.features.importantNumbers.ImportantNumbersActivity
import com.alaa.alaagallo.view.features.importantNumbers.SelectedNumber

class NumberServicesActivity : ComponentActivity() {

    private val numberName: TextView get() = findViewById(R.id.tv_number_name)
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val numberServicesRecyclerView : RecyclerView get() = findViewById(R.id.number_services_recycler_view)

    private val numberServicesAdapter = NumberServicesAdapter()
    private val numberServicesList = arrayListOf<NumberService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_number_services)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        setDummyData()
    }

    private fun setDummyData() {
        for (i in 0..3){
            numberServicesList.add(NumberService)
        }

        numberServicesAdapter.setData(numberServicesList)
    }

    private fun initView() {

        numberServicesRecyclerView.layoutManager = LinearLayoutManager(this)
        numberServicesRecyclerView.adapter = numberServicesAdapter

        numberName.text = SelectedNumber.numberName

        back.setOnClickListener {
            Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show()
            openActivity(this, ImportantNumbersActivity::class.java)
            finishAffinity()
        }
    }
}