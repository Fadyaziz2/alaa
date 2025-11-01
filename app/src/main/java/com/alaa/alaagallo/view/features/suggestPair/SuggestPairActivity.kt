package com.alaa.alaagallo.view.features.suggestPair

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.handleImages
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.suggest_compatibilty.SuggestCompatibiltyModel
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.suggestPair.SuggestBrand.SuggestBrandActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestModel.SuggestModelActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestType.SuggestTypeActivity
import com.alaa.alaagallo.view.home.HomeActivity

class SuggestPairActivity : ComponentActivity() {

    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val compatibiltyInput: EditText get() = findViewById(R.id.et_pair)
    private val compatibiltyNoteInput: EditText get() = findViewById(R.id.et_notes)
    private val sendSuggestion: Button get() = findViewById(R.id.btn_send_suggestion)
    private val chooseType: ConstraintLayout get() = findViewById(R.id.type_layout)
    private val chooseModel: ConstraintLayout get() = findViewById(R.id.model_layout)
    private val chooseBrand: ConstraintLayout get() = findViewById(R.id.brand_layout)
    private val anotherChooseBrand: ConstraintLayout get() = findViewById(R.id.another_brand_layout)
    private val typeImage: ImageView get() = findViewById(R.id.img_type)
    private val typeArrow: ImageView get() = findViewById(R.id.arrow_type)
    private val modelImage: ImageView get() = findViewById(R.id.img_model)
    private val modelArrow: ImageView get() = findViewById(R.id.arrow_model)
    private val brandImage: ImageView get() = findViewById(R.id.img_brand)
    private val anotherBrandImage: ImageView get() = findViewById(R.id.another_img_brand)
    private val brandArrow: ImageView get() = findViewById(R.id.arrow_brand)
    private val anotherBrandArrow: ImageView get() = findViewById(R.id.another_arrow_brand)
    private val chooseTypeTV: TextView get() = findViewById(R.id.tv_choose_type)
    private val chooseBrandTV: TextView get() = findViewById(R.id.tv_choose_brand)
    private val anotherChooseBrandTV: TextView get() = findViewById(R.id.another_tv_choose_brand)
    private val chooseModelTV: TextView get() = findViewById(R.id.tv_choose_model)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)

    //    val intent= Intent();
    var modelId: String = ""
    var brandId: String = ""
    var anotherBrandId: String = ""
    var typeId: String = ""
    var typeName: String = ""
    var token: String = ""
    var modelName: String = ""
    var brandName: String = ""
    var anotherBrandName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggest_pair)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val v: Int = intent.getIntExtra("home", 0)
        if (v == 1)
            clearTextViews()

        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]
        loading.visibility = View.GONE
        initView()
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        modelId = sharedPreferences.getString("mod_id", "").toString()
        modelName = sharedPreferences.getString("mod_name", "").toString()

        brandId = sharedPreferences.getString("brand_id", "").toString()
        brandName = sharedPreferences.getString("brand_name", "").toString()
        anotherBrandId = sharedPreferences.getString("another_brand_id", "").toString()
        anotherBrandName = sharedPreferences.getString("another_brand_name", "").toString()

        typeId = sharedPreferences.getString("type_id", "").toString()
        typeName = sharedPreferences.getString("type_name", "").toString()

        token = sharedPreferences.getString("token", null).toString()
        initTextViews()
        sendSuggestion.setOnClickListener {
            if (!modelId.isNullOrEmpty() && !brandId.isNullOrEmpty() && !anotherBrandId.isNullOrEmpty() && !typeId.isNullOrEmpty() && !compatibiltyInput.text.isNullOrEmpty() && !compatibiltyNoteInput.text.isNullOrEmpty()) {
//                sendSuggest(token,SuggestCompatibiltyModel(typeId,brandId,modelId,compatibiltyInput.text.toString(),compatibiltyNoteInput.text.toString()))
                sendSuggest(
                    "Bearer " + token,
                    SuggestCompatibiltyModel(
                        typeId,
                        brandId,
                        modelId,
                        anotherBrandId,
                        compatibiltyInput.text.toString(),
                        compatibiltyNoteInput.text.toString()
                    )
                )

            } else {
                Toast.makeText(this, "من فضلك تأكد من جميع البيانات المطلوبة", Toast.LENGTH_LONG)
                    .show();

            }
        }
    }

    private fun sendSuggest(token: String, suggestCompatibiltyModel: SuggestCompatibiltyModel) {

        viewModel.suggestAnCompaitbility(
            token, suggestCompatibiltyModel
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
//                    Log.d("tesSuggest",it.message)
                    loading.visibility = View.GONE
                    Toast.makeText(this, "تم ارسال اقتراحك بنجاح", Toast.LENGTH_LONG).show();
                    clearTextViews()
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.GONE
                    Log.d("errrorrrSuggest", it.message)
                    clearTextViews()
                    //                    loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
//                    loading.visibility = View.VISIBLE
//                    login.visibility = View.GONE
                    loading.visibility = View.GONE
                }

                Resource.Status.EMPTY -> {}
            }
        })
    }

    private fun initTextViews() {
        if (typeName.isNullOrEmpty()) {
            chooseTypeTV.text = "اختر الصنف"
        } else {
            chooseTypeTV.text = typeName
        }
        if (brandName.isNullOrEmpty()) {
            chooseBrandTV.text = "اختر الماركة"
        } else {
            chooseBrandTV.text = brandName

        }
        if (anotherBrandName.isNullOrEmpty()) {
            anotherChooseBrandTV.text = "اختر الماركة"
        } else {
            anotherChooseBrandTV.text = anotherBrandName
        }
        if (modelName.isNullOrEmpty()) {
            chooseModelTV.text = getString(R.string.choose_model)
        } else {

            chooseModelTV.text = modelName
        }


    }

    private fun clearTextViews() {
        chooseTypeTV.text = "اختر الصنف"
        chooseBrandTV.text = "اختر الماركة"
        anotherChooseBrandTV.text = "اختر الماركة"
        chooseModelTV.text = getString(R.string.choose_model)
        compatibiltyInput.text.clear()
        compatibiltyNoteInput.text.clear()
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("brand_name", "").apply()
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("another_brand_name", "").apply()
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("mod_name", "").apply()
        getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            .edit().putString("type_name", "").apply()

    }

    private fun initView() {

        //Handle Type Image
        handleImages(Suggestion.typeImage, typeImage, typeArrow)

        //Handle Brand Image
        handleImages(Suggestion.brandImage, brandImage, brandArrow)

        //Handle anotherBrand Image
        handleImages(Suggestion.anotherBrandImage, anotherBrandImage, anotherBrandArrow)

        //Handle Model Image
        handleImages(Suggestion.modelImage, modelImage, modelArrow)

        chooseType.setOnClickListener {

            openActivity(this, SuggestTypeActivity::class.java)
            finish()
        }

        chooseBrand.setOnClickListener {
            openActivity(this, SuggestBrandActivity::class.java)
            finish()
        }
        anotherChooseBrand.setOnClickListener {
            startActivity(Intent(this, SuggestBrandActivity::class.java).putExtra("another", true))
//            openActivity(this, SuggestBrandActivity::class.java)
            finish()
        }

        chooseModel.setOnClickListener {
            val intent = Intent(this, SuggestModelActivity::class.java)
            intent.putExtra("modelBrandd", brandId)
            startActivity(intent)
            finish()
            //openActivity(this, SuggestModelActivity::class.java)
            //finish()
        }

        back.setOnClickListener {
            openActivity(this, HomeActivity::class.java)
            finishAffinity()
        }

    }

}