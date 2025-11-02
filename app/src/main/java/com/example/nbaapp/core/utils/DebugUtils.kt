package com.example.nbaapp.core.utils

import android.content.Context
import android.util.Log

object DebugUtils {
    private const val DEBUG_TAG = "DEBUG_BRLK"
    private const val SHORT = 500L
    private const val DEFAULT = 1000L
    private const val LONG = 2000L

    fun reportDebug(msg: String) {
        Log.d(DEBUG_TAG, msg)
    }

    fun fakeLag() {
        Thread.sleep(DEFAULT)
    }

    fun fakeLag(time: Long) {
        Thread.sleep(time)
    }

    fun mark(context: Context) {
        Log.d(DEBUG_TAG, "Passou por aqui: ${context.javaClass.simpleName}")
    }
}
