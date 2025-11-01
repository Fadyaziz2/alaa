package com.alaa.alaagallo.view.features.suggestPair.SuggestModel

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
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.model.model.Model
import com.alaa.alaagallo.model.modelss.DataModels
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.pairs.pairType.model.PairModelAdapter
import com.alaa.alaagallo.view.features.suggestPair.SuggestPairActivity
import com.alaa.alaagallo.view.home.IHome
import com.alaa.alaagallo.view.home.search_comp.SearchActivity

@Suppress("DEPRECATION")
class SuggestModelActivity : ComponentActivity(), IHome {
    private val searchEditText: EditText get() = findViewById(R.id.et_search)

    private val modelRecyclerView: RecyclerView get() = findViewById(R.id.model_recycler_view)
    private val back: ImageView get() = findViewById(R.id.iv_back)

    // old private val modelsAdapter = SuggestModelAdapter(this)
    private val modelsList = arrayListOf<Model>()
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)
    // old private var pairsList : MutableList<DataModels> = mutableListOf()
    private var pairsList2 : MutableList<DataModels> = mutableListOf()
    //private var pairsList : MutableList<com.ibrahimm.alaagallo.model.showbrands.Model> = mutableListOf()

    // old private var filteredPairsList: List<DataModels> = listOf()
    //private var filteredPairsList: List<Model> = listOf()

    private val modelsAdapter = PairModelAdapter(this)
    private var pairsList : MutableList<com.alaa.alaagallo.model.showbrands.Model> = mutableListOf()

    private var filteredPairsList: List<com.alaa.alaagallo.model.showbrands.Model> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggest_model)
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
        getAllBrands("Bearer "+token);
    }

    override fun openActivity() {
//        openActivity(this, SuggestPairActivity::class.java)
//        finishAffinity()
    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit()
            .putString("mod_id", id)
            .putString("mod_name", position)
            .apply()
        startActivity(Intent(this,SuggestPairActivity::class.java));
        finish();
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        modelRecyclerView.layoutManager = GridLayoutManager(this, 2)
        modelRecyclerView.adapter = modelsAdapter
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPairs(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        back.setOnClickListener {
            val isSearch = intent.getStringExtra("search")
            if(isSearch!=null && isSearch .equals("1")){
                startActivity(Intent(this, SearchActivity::class.java))
            }else{
                onBackPressed()
            }
        }
    }

    private fun filterPairs(query: String) {
        val filteredList = if (query.isEmpty()) {
            pairsList
        } else {
            pairsList.filter { it.name.contains(query, true) }
        }
        filteredPairsList = filteredList
        modelsAdapter.setData(filteredPairsList)
    }

    private fun getAllBrands( token  :  String) {
        var getBrandId=intent.getStringExtra("modelBrandd").toString()

        viewModel.getAllBrandsById(
            token,getBrandId
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE

//                    Log.d("getDaaHOME",it.data!!.data[0].name)

//                    modelsAdapter.setData(it.data!!.data)
                    pairsList = it.data!!.data.models.toMutableList()
                    filterPairs(searchEditText.text.toString())
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.VISIBLE
                    Log.d("dddaaa",it.message)
                    //                    loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
//                    login.visibility = View.GONE
                }

                Resource.Status.EMPTY -> {}
            }
        })
    }

    /*


    private fun getModels(token: String, brandId: String){
        var getBrandId=intent.getStringExtra("modelBrandd").toString()

        viewModel.getAllBrandsById(token, brandId).observe(this, {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }

                Resource.Status.EMPTY ->{}
            }
        })
    }

     */

    /*

    private fun getAllModels( token  :  String) {



        var getBrandId=intent.getStringExtra("modelBrandd").toString()


        viewModel.getAllBrandsById(token, getBrandId).observe(this,{
            when(it.status){
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                    pairsList = it.data!!.data.models.toMutableList()
                    filterPairs(searchEditText.text.toString())
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.VISIBLE
                    Log.d("dddaaa",it.message)
                }

                Resource.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }

                Resource.Status.EMPTY -> {}
            }
        })

         */


        /* old
        viewModel.getAllModels(
            token
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                    pairsList = it.data!!.data.toMutableList()
                    filterPairs(searchEditText.text.toString())
                    //filterPairs(getBrandId)

                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.VISIBLE
                    //                    loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
//                    login.visibility = View.GONE
                }

                Resource.Status.EMPTY -> {}
            }
        })


    }
    private fun filterPairs(query: String) {
        val filteredList = if (query.isEmpty()) {
            pairsList
        } else {
            pairsList.filter { it.name.contains(query, true) }
        }
        filteredPairsList = filteredList
        modelsAdapter.setData(filteredPairsList)
    }
    private fun initView() {
        modelRecyclerView.layoutManager = GridLayoutManager(this, 2)
        modelRecyclerView.adapter = modelsAdapter
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPairs(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        back.setOnClickListener {
            openActivity()
        }

         */

}