package com.alaa.alaagallo.base.helpers.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * Extensions for reducing code inside fragments
 */
fun <T> Fragment.openActivityFromParent(cls: Class<T>) {
    activity?.openActivity(activity!!, cls)
}

fun Fragment.makeLongToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}