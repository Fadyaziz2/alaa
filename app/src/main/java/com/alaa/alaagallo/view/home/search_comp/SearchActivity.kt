package com.alaa.alaagallo.view.home.search_comp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaa.Constants
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.handleImages
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.sub_categories.SubCategoriesModelResponse
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.pairs.SelectedPairType
import com.alaa.alaagallo.view.features.pairs.pairType.brand.PairBrandActivity
import com.alaa.alaagallo.view.features.pairs.pairType.interaction.SearchInteraction
import com.alaa.alaagallo.view.features.pairs.pairType.model.PairModelActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestType.SuggestTypeActivity
import com.alaa.alaagallo.view.home.HomeActivity
import com.alaa.alaagallo.view.registration.LoginActivity
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class SearchActivity : ComponentActivity(), SearchInteraction {

    private val pairName: TextView get() = findViewById(R.id.pair_name)
    private val chooseTypeTV: TextView get() = findViewById(R.id.tv_choose_type)
    private val chooseBrandTV: TextView get() = findViewById(R.id.tv_choose_brand)
    private val chooseModelTV: TextView get() = findViewById(R.id.tv_choose_model)
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val chooseBrand: ConstraintLayout get() = findViewById(R.id.brand_layout)
    private val chooseModel: ConstraintLayout get() = findViewById(R.id.model_layout)
    private val typeChoose: ConstraintLayout get() = findViewById(R.id.type_layout)
    private val brandImage: ImageView get() = findViewById(R.id.img_brand)
    private val modelImage: ImageView get() = findViewById(R.id.img_model)
    private val arrowBrand: ImageView get() = findViewById(R.id.arrow_brand)
    private val btn_search: Button get() = findViewById(R.id.btn_search)
    private val arrowModel: ImageView get() = findViewById(R.id.arrow_model)
    private val pairResultRecyclerView: RecyclerView get() = findViewById(R.id.pair_result_recycler_view)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)

    var modelId: String = ""
    var modelName: String = ""

    var brandId: String = ""
    var brandName: String = ""

    var typeId: String = ""
    var typeName: String = ""

    var token: String = ""
    private val pairResultAdapter = SearchAdapter(this)
    private val pairResultList = arrayListOf<SubCategoriesModelResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel =
            ViewModelProvider(this)[PairsViewModel::class.java]
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        modelId = sharedPreferences.getString("mod_id", "").toString()
        modelName = sharedPreferences.getString("mod_name_search", "").toString()
        Log.d("modl", modelName)

        brandId = sharedPreferences.getString("brand_id", "").toString()
        brandName = sharedPreferences.getString("type_name_search", "").toString()


        typeId = sharedPreferences.getString("type_id", "").toString()
        typeName = sharedPreferences.getString("type_name", "").toString()


        token = sharedPreferences.getString("token", null).toString()
        initView()
        initTextViews()
        loading.visibility = View.GONE

        btn_search.setOnClickListener {
            Toast.makeText(this, "hi", Toast.LENGTH_LONG).show()
            loading.visibility = View.VISIBLE
            Log.d("testtttttt",typeId +"--"+ brandId+"--"+modelId)
            if (modelId.isNotEmpty() && brandId.isNotEmpty() && typeId.isNotEmpty()&& modelName.isNotEmpty() && brandName.isNotEmpty()) {
                search("Bearer " + token,
                    brandId, modelId, typeId
                )
            } else {
                Toast.makeText(this, "من فضلك تأكد من جميع البيانات المطلوبة", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun initView() {
        pairName.text = SelectedPairType.pairName
        pairResultRecyclerView.layoutManager = GridLayoutManager(this, 2)
        pairResultRecyclerView.adapter = pairResultAdapter

        // Handle Brand Image
        handleImages(SelectedPairType.brandImage, brandImage, arrowBrand)

        // Handle Model Image
        handleImages(SelectedPairType.modelImage, modelImage, arrowModel)

        typeChoose.setOnClickListener {
            val intent = Intent(this, SuggestTypeActivity::class.java)
            intent.putExtra("search", "1")
            startActivity(intent)
        }

        back.setOnClickListener {
            openActivity(this, HomeActivity::class.java)

            finishAffinity()
        }

        chooseBrand.setOnClickListener {
            val intent = Intent(this, PairBrandActivity::class.java)
            intent.putExtra("search", "1")
            startActivity(intent)
        }

        chooseModel.setOnClickListener {
            if (brandName.isNotEmpty()) {
                val intent = Intent(this, PairModelActivity::class.java)
                intent.putExtra("search", "1")
                startActivity(intent)
            } else {
                Toast.makeText(this, "من فضلك تأكد من جميع البيانات المطلوبة", Toast.LENGTH_LONG)
                    .show()
            }
        }
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            viewModel.isUserExpired(
                "Bearer $token"
            ).observe(this) { s ->
                when (s.status) {
                    Resource.Status.SUCCESS -> {
                    }

                    Resource.Status.ERROR -> {
                        s.message?.let {
                            makeLongToast(s.message)
                        }
                        if(Constants.isExpired){
                            Constants.isExpired = false
                            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", null)
                                .putString("name", null)
                                .putString("end_date", null)
                                .apply()
                            makeLongToast("انتهت صلاحية الاشتراك")
                            openActivity(this, LoginActivity::class.java)
                            finishAffinity()
                        }
                    }

                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.EMPTY -> {}
                }
            }
        }
    }

    private fun search(token: String, brand_id: String, mod_id: String, type_id: String) {
        viewModel.searchInCompitabilty(
            token, brand_id, mod_id, type_id
        )
//            .observe(this, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    loading.visibility = View.GONE
////                    Log.d("ddd", it.data!!.data.compatibilities.toString())
//                    if (it.data!!.data.isNullOrEmpty()) {
//                        Toast.makeText(this, "لا يوجد توافقات لهذا الاختيار", Toast.LENGTH_SHORT).show()
//                    } else {
////                        pairResultAdapter.setData(it.data!!.data.compatibilities)
//                    }
//                    clearTextViews()  // تأكد من مسح الـ TextViews و SharedPreferences هنا
//
//                }
//
//                Resource.Status.ERROR -> {
//                    makeLongToast(it.message!!)
//                    Toast.makeText(this, "لا يوجد توافقات لهذا الاختيار", Toast.LENGTH_SHORT).show()
//                    loading.visibility = View.GONE
//                    clearTextViews()  // تأكد من مسح الـ TextViews و SharedPreferences هنا
//                }
//
//                Resource.Status.LOADING -> {
//                    loading.visibility = View.GONE
//                }
//
//                Resource.Status.EMPTY -> {
//                    loading.visibility = View.GONE
//                }
//            }
//        })
    }

    private fun initTextViews() {
        chooseTypeTV.text = typeName
        chooseBrandTV.text = brandName
        chooseModelTV.text = modelName
    }

    private fun clearTextViews() {
        typeName=""
        brandName=""
        modelName=""
        chooseTypeTV.text = "اختر الصنف"
        chooseBrandTV.text = "اختر الماركة"
        chooseModelTV.text = getString(R.string.choose_model)
        val sharedPrefs = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString("type_name", "")
            putString("mod_name_search", "")
            putString("type_name_search", "")
            apply()
        }
    }

    @SuppressLint("InflateParams", "SetJavaScriptEnabled")
    override fun onClickShowImage(url: String) {
        val view = layoutInflater.inflate(R.layout.layout_dialog_image, null)
        val image = view.findViewById<ImageView>(R.id.imageDialog)
        val webView: WebView = view.findViewById(R.id.myWebView)
        webView.settings.javaScriptEnabled = true

        // Ensure WebView opens links within the app
        webView.webViewClient = WebViewClient()
        Dialog(this).apply {
            webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null)
            setContentView(view)
            window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
            show()
        }
    }

    @SuppressLint("InflateParams")
    override fun onClickImage(url: String) {
        val view = layoutInflater.inflate(R.layout.layout_dialog_photo_image, null)
        val image = view.findViewById<ImageView>(R.id.photoDialog)
        Picasso.get()
            .load(url)
            .into(image)
        Dialog(this).apply {
            setContentView(view)
            window?.setBackgroundDrawableResource(R.drawable.bg_photo_view)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            show()
        }
    }
}
