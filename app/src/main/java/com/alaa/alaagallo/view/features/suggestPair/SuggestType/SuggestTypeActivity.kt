package com.alaa.alaagallo.view.features.suggestPair.SuggestType

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
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.pairs.pairType.PairTypeActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestPairActivity
import com.alaa.alaagallo.view.home.IHome
import com.alaa.alaagallo.view.home.search_comp.SearchActivity

class SuggestTypeActivity : ComponentActivity() , IHome{

    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val typesRecyclerView: RecyclerView get() = findViewById(R.id.types_recycler_view)
    private val suggestTypeAdapter = SuggestTypeAdapter(this)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggest_type)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]
        initView()
//        setDummyData()
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        getAllCategories("Bearer "+token);
    }

//    private fun setDummyData() {
//        for (i in 0..2) {
//            typesList.add(Type)
//        }
//        suggestTypeAdapter.setData(typesList)
//    }

    override fun openActivity() {
//        openActivity(this, SuggestPairActivity::class.java)

    }

    override fun sendSubCategoriesData(position: String, id: String, url:String) {
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit()
            .putString("type_id", id)
            .putString("type_name",position)
            .apply()
        var isPairType=intent.getStringExtra("pairType")
        if(isPairType.equals("1")){
            startActivity(Intent(this, PairTypeActivity::class.java));

        }else {

            startActivity(Intent(this, SuggestPairActivity::class.java));
            finish();
        }
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }


    private fun initView() {
        //Initialize recycler view
        typesRecyclerView.layoutManager = GridLayoutManager(this, 2);
        typesRecyclerView.adapter = suggestTypeAdapter

        back.setOnClickListener {
            val isSearch = intent.getStringExtra("search")
            if(isSearch!=null && isSearch .equals("1")){
                startActivity(Intent(this,SearchActivity::class.java))
            }else{
                openActivity(this, SuggestPairActivity::class.java)
                finishAffinity()
            }

        }
    }

    private fun getAllCategories( token  :  String) {

        viewModel.getAllCategories(
            token
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loading.visibility = View.GONE
//                    com.ibrahimm.alaagallo.base.helpers.extensions.getSharedPreferences(this).edit()
//                        .putString("token", Home.token).apply()
//                    openActivity(this, HomeActivity::class.java)
//                    finishAffinity()
//                    Log.d("fhhh", it.data?.data.toString())
//                    listOfCategories.add()
//                    it.data?.data?.
                    suggestTypeAdapter.setData(it.data!!.data)

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