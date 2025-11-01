package com.alaa.alaagallo.view.features.suggestPair.SuggestBrand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.alaa.alaagallo.model.brand.Brand
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.suggestPair.SuggestPairActivity
import com.alaa.alaagallo.view.home.IHome

class SuggestBrandActivity : ComponentActivity(), IHome {

    private val brandsRecyclerView: RecyclerView get() = findViewById(R.id.brand_recycler_view)
    private val back : ImageView get() = findViewById(R.id.iv_back)

    private val brandsAdapter = SuggestBrandAdapter(this)
    private val brandsList = arrayListOf<Brand>()
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggest_brand)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel =
            ViewModelProvider(this)[PairsViewModel::class.java]

        initView()

//        setDummyData()
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        getAllBrands("Bearer "+token);
    }

    override fun openActivity() {
//        openActivity(this, SuggestPairActivity::class.java)
//        finishAffinity()
    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        val isAnotherBrand = intent.getBooleanExtra("another",false)
        if (isAnotherBrand){
            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                .edit()
                .putString("another_brand_id", id)
                .putString("another_brand_name", position)
                .apply()
        }
        else{
            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                .edit()
                .putString("brand_id", id)
                .putString("brand_name", position)
                .apply()
        }
        startActivity(Intent(this,SuggestPairActivity::class.java));
        finish()
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }



    private fun getAllBrands( token  :  String) {

        viewModel.getAllBrands(
            token
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE

                    brandsAdapter.setData(it.data!!.data)
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
    private fun initView() {
        //initialize recycler view
        brandsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        brandsRecyclerView.adapter = brandsAdapter

        back.setOnClickListener {
            openActivity()
        }

    }
}