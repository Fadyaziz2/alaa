package com.alaa.alaagallo.view.features.pairs.pairType.brand

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
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
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.brands.BrandsResponse
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.pairs.pairType.PairTypeActivity
import com.alaa.alaagallo.view.home.IHome
import com.alaa.alaagallo.view.home.search_comp.SearchActivity

@Suppress("DEPRECATION")
class PairBrandActivity : ComponentActivity(), IHome {
    lateinit var viewModel: PairsViewModel

    private val brandsRecyclerView: RecyclerView get() = findViewById(R.id.brand_recycler_view)
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val loading: ProgressBar get() = findViewById(R.id.loading)

    private val brandsAdapter = PairBrandsAdapter(this)
    private val brandsList = arrayListOf<BrandsResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pair_brand)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Navigate directly to TypeActivity
                val intent = Intent(this@PairBrandActivity, PairTypeActivity::class.java)
                startActivity(intent)
                finish() // Remove ModelActivity from the stack
            }
        })
        viewModel =
            ViewModelProvider(this)[PairsViewModel::class.java]
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val gettoken = sharedPreferences.getString("token", null)
        var token = "Bearer " + gettoken
        getAllBrands(token)
        initView()
//        setDummyData()
    }

    override fun openActivity() {
        openActivity(this, PairTypeActivity::class.java)
        finish()
    }

    override fun sendSubCategoriesData(position: String, id: String, url: String) {
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit()
            .putString("brand_id", id)
            .putString("brand_image", url)
            .putString("type_name_search", position)
            .apply()
        if (intent.getStringExtra("paa") == "1") {
            startActivity(Intent(this, PairTypeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }

//    private fun setDummyData() {
//        for (i in 0..2) {
//            brandsList.add(Brand)
//        }
//
//        brandsAdapter.setData(brandsList)
//    }

    private fun initView() {
        //initialize recycler view
        //brandsRecyclerView.layoutManager = LinearLayoutManager(this)
        brandsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        brandsRecyclerView.adapter = brandsAdapter

        back.setOnClickListener {
            val isSearch = intent.getStringExtra("search")
            if (isSearch != null && isSearch == "1") {
                startActivity(Intent(this, SearchActivity::class.java))
            } else {
                onBackPressed()
            }
        }

    }

    private fun getAllBrands(token: String) {

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
}