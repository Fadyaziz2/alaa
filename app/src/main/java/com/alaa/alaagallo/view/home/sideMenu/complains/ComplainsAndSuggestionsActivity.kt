package com.alaa.alaagallo.view.home.sideMenu.complains

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.base.helpers.extensions.makeLongToast
import com.alaa.alaagallo.model.complaints.ComplaintsModel
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ComplainsAndSuggestionsActivity : ComponentActivity() {

    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val nameCom: EditText get() = findViewById(R.id.et_name)
    private val phoneCom: EditText get() = findViewById(R.id.et_phone)
    private val notesCom: EditText get() = findViewById(R.id.et_notes)
    private val sendComplaints: Button get() = findViewById(R.id.send_comp)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_complains_and_suggestions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)

        initView()
        loading.visibility = View.GONE
        sendComplaints.setOnClickListener {
            if (nameCom.text.isNotEmpty() && phoneCom.text.isNotEmpty() && notesCom.text.isNotEmpty()) {
                sendComplaints.visibility = View.GONE
                loading.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    val complaintsModel = ComplaintsModel(
                        nameCom.text.toString(),
                        phoneCom.text.toString(),
                        notesCom.text.toString()
                    )
                    val token = sharedPreferences.getString("token", null).toString()
                    sendComplaintsFun("Bearer $token", complaintsModel)
                }
            } else {
                Toast.makeText(this, "من فضلك تأكد من جميع البيانات المطلوبة", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initView() {
        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun sendComplaintsFun(token: String, complaintsModel: ComplaintsModel) {

        viewModel.sendComplaints(
            token, complaintsModel
        ).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
//                    Log.d("tesSuggest",it.message)
                    loading.visibility = View.GONE
                    sendComplaints.visibility = View.VISIBLE
                    Toast.makeText(this, "تم ارسال شكوتك بنجاح", Toast.LENGTH_LONG).show();
                }

                Resource.Status.ERROR -> {
                    makeLongToast(it.message!!)
                    loading.visibility = View.GONE
                    sendComplaints.visibility = View.VISIBLE
                    //                    loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
//                    loading.visibility = View.VISIBLE
//                    login.visibility = View.GONE
                    loading.visibility = View.GONE
                    sendComplaints.visibility = View.VISIBLE
                }

                Resource.Status.EMPTY -> {}
            }
        })
    }
}