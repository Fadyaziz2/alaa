package com.alaa.alaagallo.view.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import com.alaa.Constants
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.LaunchingActivity
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.view.home.HomeActivity
import com.alaa.alaagallo.view.onboarding.OnboardingActivity
import com.alaa.alaagallo.base.helpers.extensions.getSharedPreferences
import com.alaa.alaagallo.view.home.Home
import com.alaa.alaagallo.view.registration.LoginActivity

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        selectActivity()
    }

    private fun selectActivity() {
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
//        val isLoggedIn = getSharedPreferences(this).contains("token")
        val isLoggedIn = token

        Handler().postDelayed({
            if (isFirstLaunch()) {
                openActivity(this, OnboardingActivity::class.java)
            } else if (!token.isNullOrEmpty()) {
                Home.token = getSharedPreferences(this).getString("token", "")!!
                Home.id = sharedPreferences.getInt("id", 0)
                Constants.TOKEN = token
                openActivity(this, HomeActivity::class.java)
                finishAffinity()
            } else {
                openActivity(this, LoginActivity::class.java)
                finishAffinity()
            }
        }, 1000)
    }


    private fun isFirstLaunch(): Boolean {
        return LaunchingActivity.getInstance(applicationContext).isFirstLaunch()
    }
}

