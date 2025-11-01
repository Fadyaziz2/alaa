package com.alaa.alaagallo.view.features.pairs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.AdapterToViewCallBack
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.typess.Data
import com.alaa.alaagallo.view.features.pairs.pairType.PairTypeActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestType.SuggestTypeAdapter
import com.alaa.alaagallo.view.home.HomeActivity
import com.alaa.alaagallo.view.home.IHome

class PairsActivity : ComponentActivity(), AdapterToViewCallBack, IHome {
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val searchEditText: EditText get() = findViewById(R.id.et_search)
    private val pairsRecyclerView: RecyclerView get() = findViewById(R.id.pairs_recycler_view)
    private val pairsAdapter = PairsAdapter(this, this)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)
    private var pairsList: MutableList<Data> = mutableListOf()
    private var filteredPairsList: List<Data> = listOf()
    private var suggestTypeAdapter = SuggestTypeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pairs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]

        initView()
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        getAllCategories("Bearer " + token)
    }

    override fun openActivity() {
        openActivity(this, PairTypeActivity::class.java)
    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        val intent = Intent(this, PairTypeActivity::class.java)
        intent.putExtra("idType", id)
        intent.putExtra("nameType", position)
        intent.putExtra("pairs", 1)
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("idType", id).apply()
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("nameType", position).apply()
        Log.d("fdabefore", "$position ----> $id")
        startActivity(intent)
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: com.alaa.alaagallo.model.newnumbers.Data) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        // Initialize recycler view
        loading.visibility = View.GONE
        pairsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        pairsRecyclerView.adapter = suggestTypeAdapter

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
            pairsList
        } else {
            pairsList.filter { it.name.contains(query, true) }
        }
        filteredPairsList = filteredList
        suggestTypeAdapter.setData(filteredPairsList)
    }

    private fun getAllCategories(token: String) {
        viewModel.getAllCategories(token).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                    pairsList = it.data!!.data.toMutableList()
                    filterPairs(searchEditText.text.toString())
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }

                Resource.Status.EMPTY -> {}
            }
        })
    }
}