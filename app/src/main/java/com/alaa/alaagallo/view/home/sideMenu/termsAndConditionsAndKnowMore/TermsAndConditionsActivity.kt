package com.alaa.alaagallo.view.home.sideMenu.termsAndConditionsAndKnowMore

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alaa.alaagallo.R

class TermsAndConditionsActivity : ComponentActivity() {

    private val back : ImageView get() = findViewById(R.id.iv_back)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_terms_and_conditions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}