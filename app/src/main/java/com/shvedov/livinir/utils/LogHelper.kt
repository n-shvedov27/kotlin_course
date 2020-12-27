package com.shvedov.livinir.utils

import android.util.Log

object LogHelper {

    private const val TAG = "MY_DEBUG"

    fun logD(message: String) = Log.d(TAG, message)
    fun logE(message: String) = Log.e(TAG, message)
    fun logI(message: String) = Log.i(TAG, message)
}