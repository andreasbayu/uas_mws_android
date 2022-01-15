package com.andbayu.mws_uas.utils

import android.content.Context
import android.widget.Toast

object Global {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}