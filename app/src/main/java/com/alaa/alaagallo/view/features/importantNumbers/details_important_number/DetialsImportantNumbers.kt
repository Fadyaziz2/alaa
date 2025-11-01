package com.alaa.alaagallo.view.features.importantNumbers.details_important_number

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.model.newnumbers.Number
import com.alaa.alaagallo.model.newnumbers.NumberX
import com.alaa.alaagallo.view.features.importantNumbers.DetailsImportantNumbersAdapter
import com.alaa.alaagallo.view.features.importantNumbers.ImportantNumbersActivity
import com.alaa.alaagallo.view.home.IHome
import com.alaa.alaagallo.view.home.NumbersInterface

class DetialsImportantNumbers : ComponentActivity(), IHome ,NumbersInterface{
    private val spinner: Spinner get() = findViewById(R.id.spinner_numbers)
    private val detailsRec: RecyclerView get() = findViewById(R.id.pairs_recycler_view)
    private val searchEditText: EditText get() = findViewById(R.id.et_search)
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private var normalListNumbers: List<NumberX> = listOf()
    private var filteredListNumbers: List<NumberX> = listOf()
    private val detailsImportantNumbersAdapter = DetailsImportantNumbersAdapter(this)
    private lateinit var adapter : CustomSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detials_important_numbers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        back.setOnClickListener {
            openActivity(this, ImportantNumbersActivity::class.java)
            finishAffinity()
        }
        detailsRec.layoutManager = LinearLayoutManager(this)
        detailsRec.adapter = detailsImportantNumbersAdapter
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPairs(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        val listNumbers = intent.getSerializableExtra("listOfNumbers") as? Data
        try {
            Log.d("guessThis?", listNumbers.toString())
        } catch (e: Exception) {
            Log.d("guessThis", e.message.toString())
        }
        if (listNumbers != null) {
            adapter = CustomSpinnerAdapter(this, listNumbers.numbers.toMutableList())
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    try {
                        // Cast to Number instead of NumberX
                        val selectedItem = parent.getItemAtPosition(position) as Number
                        // Retrieve the list of NumberX objects
                        Log.d("checkdd", selectedItem.toString())
                        // Pass the list of NumberX objects to the adapter
                        detailsImportantNumbersAdapter.setData(selectedItem.numbers)
                        normalListNumbers = selectedItem.numbers
                    } catch (e: Exception) {
                        Log.d("checkddError", e.message.toString())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
        }

    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        TODO("Not yet implemented")
    }


    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }

    override fun sendNumberToCall(listNumbers: NumberX) {
        val phoneNumber = listNumbers.number
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        startActivity(intent)
    }
    private fun filterPairs(query: String) {
        val filteredList = if (query.isEmpty()) {
            normalListNumbers
        } else {
            normalListNumbers.filter { it.name.contains(query, true) }
        }
        filteredListNumbers = filteredList
        detailsImportantNumbersAdapter.setData(filteredList)
    }
}
