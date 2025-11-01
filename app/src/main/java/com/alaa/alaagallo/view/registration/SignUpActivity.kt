package com.alaa.alaagallo.view.registration

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.view.home.HomeActivity

class SignUpActivity : ComponentActivity() {

    private val alreadyHaveAccount: TextView get() = findViewById(R.id.tv_have_account)
    private val signUp: Button get() = findViewById(R.id.btn_sign_up)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        alreadyHaveAccount.setOnClickListener {
            openActivity(this, LoginActivity::class.java)
            finishAffinity()
        }

        signUp.setOnClickListener {
            //TODO:: Sign up
            openActivity(this, HomeActivity::class.java)
            finishAffinity()
        }
    }
}