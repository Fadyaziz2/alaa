package com.alaa.alaagallo.base.helpers.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso


fun <T> Activity.openActivity(context: Context, cls: Class<T>) {
    startActivity(Intent(context, cls))
}

fun <T> Activity.openActivity(intent: Intent) {
    startActivity(intent)
}

fun Activity.makeLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.handleImages(value: String, image: ImageView, arrow: ImageView) {
    if(value != "") {
        image.visibility = View.VISIBLE
        arrow.visibility = View.GONE
        Picasso.get()
            .load(value)
            .into(image)
    }else{
        image.visibility = View.GONE
        arrow.visibility = View.VISIBLE
    }
}

/*
@SuppressLint("ObsoleteSdkInt")
fun changeLang(context: Context, lang_code: String): ContextWrapper {
    var context = context
    val sysLocale: Locale

    val rs = context.resources
    val config = rs.configuration

    sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales.get(0)
    } else {
        config.locale
    }
    if (lang_code != "" /*&& sysLocale.language != lang_code*/) {
        val locale = Locale(lang_code)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
        } else {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config)
            config.setLayoutDirection(locale)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    return ContextWrapper(context)
}*/

fun getSharedPreferences(context: Context): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(context)