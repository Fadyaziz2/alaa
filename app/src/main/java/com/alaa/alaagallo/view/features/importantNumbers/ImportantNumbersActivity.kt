package com.alaa.alaagallo.view.features.importantNumbers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.importantNumbers.details_important_number.DetialsImportantNumbers
import com.alaa.alaagallo.view.features.importantNumbers.numberDetails.NumberServicesActivity
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.home.HomeActivity
import com.alaa.alaagallo.view.home.IHome

class ImportantNumbersActivity : ComponentActivity() , IHome{

    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val searchEditText: EditText get() = findViewById(R.id.et_search)
    private val importantNumbersRecyclerView: RecyclerView get() = findViewById(R.id.important_numbers_recycler_view)

    private val importantNumbersAdapter = ImportantNumbersAdapter(this)
//    private val importantNumbersList = arrayListOf<ImportantNumber>()
    private var importantNumbersList: MutableList<Data> = mutableListOf()
    private var filteredImportantNumbersList: List<Data> = listOf()
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_important_numbers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel =
            ViewModelProvider(this)[PairsViewModel::class.java]

        initView()
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        getAllNumbers("Bearer $token")
    }


    private fun getAllNumbers( token  :  String) {

        viewModel.getAllNumbers(
            token
        ).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE

                    importantNumbersList = it.data!!.data.toMutableList()
                    importantNumbersAdapter.setData(importantNumbersList)
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.GONE
                    //                    loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    loading.visibility = View.GONE
//                    login.visibility = View.GONE
                }

                Resource.Status.EMPTY -> {}
            }
        }
    }

    override fun openActivity() {
        openActivity(this, NumberServicesActivity::class.java)
    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        TODO("Not yet implemented")
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        val intent= Intent(this, DetialsImportantNumbers::class.java)
        intent.putExtra("listOfNumbers",listNumbers)
        startActivity(intent)
    }

    private fun initView() {
        importantNumbersRecyclerView.layoutManager = GridLayoutManager(this, 2)
        importantNumbersRecyclerView.adapter = importantNumbersAdapter

        back.setOnClickListener {
            openActivity(this, HomeActivity::class.java)
            finishAffinity()
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPairs(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterPairs(query: String) {
        val filteredList = if (query.isEmpty()) {
            importantNumbersList
        } else {
            importantNumbersList.filter { it.name.contains(query, true) }
        }
        filteredImportantNumbersList = filteredList
        importantNumbersAdapter.setData(filteredImportantNumbersList)
    }
}