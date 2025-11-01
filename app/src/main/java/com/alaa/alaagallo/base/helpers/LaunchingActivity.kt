package com.alaa.alaagallo.base.helpers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class LaunchingActivity {

    fun setIsFirstLaunchToFalse() {
        editor!!.putBoolean(IS_FIRST_LAUNCH, false)
        editor!!.commit()
    }

    fun isFirstLaunch() : Boolean {
        return sharedPreferences!!.getBoolean(IS_FIRST_LAUNCH, true)
    }
    fun saveToken(token: String) {
        editor!!.putString("token", token)
        editor!!.commit()
    }

    fun retrieveToken(): String? {
        return sharedPreferences!!.getString("token", null)
    }
    companion object {
        private val sharedPref = LaunchingActivity()
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
        private val IS_FIRST_LAUNCH = "is_first_launch"

        @Synchronized
        fun getInstance(context: Context): LaunchingActivity {

            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                editor = sharedPreferences!!.edit()
            }

            return sharedPref
        }
    }
}