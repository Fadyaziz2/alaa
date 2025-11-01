package com.alaa.alaagallo.view.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alaa.Constants
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.AdapterToViewCallBack
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.home.Category
import com.alaa.alaagallo.model.newnumbers.Data
import com.alaa.alaagallo.view.features.importantNumbers.ImportantNumbersActivity
import com.alaa.alaagallo.view.features.pairs.PairsActivity
import com.alaa.alaagallo.view.features.suggestPair.SuggestPairActivity
import com.alaa.alaagallo.view.home.sideMenu.complains.ComplainsAndSuggestionsActivity
import com.alaa.alaagallo.view.home.sideMenu.contactUs.ContactUsActivity
import com.alaa.alaagallo.view.home.sideMenu.termsAndConditionsAndKnowMore.KnowMoreActivity
import com.alaa.alaagallo.view.home.sideMenu.termsAndConditionsAndKnowMore.TermsAndConditionsActivity
import com.alaa.alaagallo.view.my_accounts_and_sale.MyAccountsAndSaleActivity
import com.alaa.alaagallo.view.registration.LoginActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class HomeActivity : ComponentActivity(), AdapterToViewCallBack, IHome {

    private val btnMenu: Button get() = findViewById(R.id.btn_menu)
    private val categoriesRecyclerView: RecyclerView get() = findViewById(R.id.categories_recycler_view)
    private var plus = true
    private val categoriesAdapter = CategoriesAdapter(this, this)
    private val categoriesList = arrayListOf<Category>()
    private val images = listOf(R.drawable.sl_one_, R.drawable.img_sl_two)
    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        initView()
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)
        val pr_one = findViewById<TextView>(R.id.home_slider_tv_indicator_one)
        val pr_two = findViewById<TextView>(R.id.home_slider_tv_indicator_two)
        val v = layoutInflater.inflate(R.layout.activity_home, null, false)
        val sliderAdapter = SliderAdapter(images)

        viewPager.adapter = sliderAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    plus = true
                    pr_one.setBackgroundResource(R.drawable.home_slider_selected_shape)
                    pr_two.setBackgroundResource(R.drawable.home_slider_not_selected_shape)
                } else {
                    plus = false
                    pr_one.setBackgroundResource(R.drawable.home_slider_not_selected_shape)
                    pr_two.setBackgroundResource(R.drawable.home_slider_selected_shape)
                }
            }
        })
        runnable = object : Runnable {
            override fun run() {
                if (plus)
                    viewPager.currentItem += 1
                else
                    viewPager.currentItem -= 1
                handler.postDelayed(this, 5000)
            }

        }
        handler.postDelayed(runnable, 5000)
        setDummyData()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun pairs() {
        openActivity(this, PairsActivity::class.java)
    }

    override fun sendSubCategoriesData(position: String, id: String, url: String) {
        TODO("Not yet implemented")
    }

    override fun sendNumbersToDetailsNumbers(listNumbers: Data) {
        TODO("Not yet implemented")
    }

    override fun suggestPair() {
        val intent: Intent = Intent(this, SuggestPairActivity::class.java)
        intent.putExtra("home", 1)
        openActivity<Intent>(intent)
    }

    override fun importantNumbers() {
        openActivity(this, ImportantNumbersActivity::class.java)
    }

    override fun searchCompa() {
//        openActivity(this, SearchActivity::class.java)
        openActivity(this, MyAccountsAndSaleActivity::class.java)
    }

    private fun setDummyData() {
        for (i in 0..3) {
            categoriesList.add(Category)
        }
        categoriesAdapter.setData(categoriesList)
    }

    @SuppressLint("CommitTransaction", "ResourceType")
    private fun initView() {
        //Initialize recycler view

        categoriesRecyclerView.layoutManager = GridLayoutManager(this, 2);
        categoriesRecyclerView.adapter = categoriesAdapter
        btnMenu.setOnClickListener {
            showSideMenu()
        }

        lifecycleScope.launch {
            viewModel.checkDataState.collectLatest {
                if (!it) {
                    getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                        .edit()
                        .putString("token", null)
                        .putString("name", null)
                        .putString("end_date", null)
                        .apply()
                    openActivity(this@HomeActivity, LoginActivity::class.java)
                    finishAffinity()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.checkDataBanned.collectLatest {
                if (it == 1) {
                    getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                        .edit()
                        .putString("token", null)
                        .putString("name", null)
                        .putString("end_date", null)
                        .apply()
                    makeLongToast("لقد تم حظر المستخدم")
                    openActivity(this@HomeActivity, LoginActivity::class.java)
                    finishAffinity()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorState.collectLatest {
                if (it.isNotEmpty())
                    makeLongToast(it)
            }
        }

        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            lifecycleScope.launch {
                viewModel.checkUserData("Bearer $token")
            }
            viewModel.isUserExpired(
                "Bearer $token"
            ).observe(this) { s ->
                when (s.status) {
                    Resource.Status.SUCCESS -> {
                    }

                    Resource.Status.ERROR -> {
//                        s.message?.let {
//                            makeLongToast(s.message)
//                        }
                        if (Constants.isExpired) {
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

    private fun showSideMenu() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.side_menu)

        initSideMenuItems(dialog)

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent)))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.END)
    }

    private fun initSideMenuItems(dialog: Dialog) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val endDate = sharedPreferences.getString("end_date", "")
        val close = dialog.findViewById<ImageView>(R.id.iv_close)
        val txtName = dialog.findViewById<TextView>(R.id.tv_username)
        val txtEndDate = dialog.findViewById<TextView>(R.id.tv_end_date)
        val knowMoreLayout = dialog.findViewById<ConstraintLayout>(R.id.know_more)
        val termsAndConditions = dialog.findViewById<ConstraintLayout>(R.id.terms_and_conditions)
        val complains = dialog.findViewById<ConstraintLayout>(R.id.complains)
        val contactUs = dialog.findViewById<ConstraintLayout>(R.id.contact_us)
        val shareApp = dialog.findViewById<ConstraintLayout>(R.id.share_app)
        val rateApp = dialog.findViewById<ConstraintLayout>(R.id.rate_app)
        val logout = dialog.findViewById<Button>(R.id.btn_logout)
        val loading: ProgressBar = dialog.findViewById(R.id.loadingLogOut)
        txtName.text = name
        txtEndDate.text = endDate
        close.setOnClickListener {
            dialog.hide()
        }

        knowMoreLayout.setOnClickListener {
            dialog.hide()
            openActivity(this, KnowMoreActivity::class.java)
        }

        termsAndConditions.setOnClickListener {
            dialog.hide()
            openActivity(this, TermsAndConditionsActivity::class.java)
        }

        complains.setOnClickListener {
            dialog.hide()
            openActivity(this, ComplainsAndSuggestionsActivity::class.java)
        }

        contactUs.setOnClickListener {
            dialog.hide()
            openActivity(this, ContactUsActivity::class.java)
        }

        logout.setOnClickListener {
            loading.visibility = View.VISIBLE
            it.visibility = View.GONE
            val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)
            if (token != null) {
                viewModel.logOut(
                    "Bearer $token"
                ).observe(this) { s ->
                    when (s.status) {
                        Resource.Status.SUCCESS -> {
                            loading.visibility = View.GONE
                            dialog.hide()
                            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                                .edit()
                                .putString("token", null)
                                .putString("name", null)
                                .putString("end_date", null)
                                .apply()
                            openActivity(this, LoginActivity::class.java)
                            finishAffinity()
                        }

                        Resource.Status.ERROR -> {
                            makeLongToast(s.message!!)
                            loading.visibility = View.GONE
                            it.visibility = View.VISIBLE
                            //                    loading.visibility = View.GONE
                        }

                        Resource.Status.LOADING -> {
                            loading.visibility = View.GONE
                            it.visibility = View.VISIBLE
                            //                    login.visibility = View.GONE
                        }

                        Resource.Status.EMPTY -> {}
                    }
                }
            }
        }

        shareApp.setOnClickListener {
            val appPackageName = packageName
            val playStoreLink = "https://play.google.com/store/apps/details?id=$appPackageName"

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, playStoreLink)
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        rateApp.setOnClickListener {
            val appPackageName = packageName
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }
}


