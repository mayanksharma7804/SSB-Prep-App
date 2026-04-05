package com.ssbprep.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import java.util.concurrent.TimeUnit

object SSBCountdownManager {
    private const val PREFS_NAME = "SSBCountdownPrefs"
    private const val KEY_SSB_DATE = "ssb_date"
    private const val KEY_SSB_NAME = "ssb_name"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveSSBDate(context: Context, name: String, timestamp: Long) {
        getPrefs(context).edit()
            .putString(KEY_SSB_NAME, name)
            .putLong(KEY_SSB_DATE, timestamp)
            .apply()
    }

    fun getSSBDate(context: Context): Long {
        return getPrefs(context).getLong(KEY_SSB_DATE, 0L)
    }

    fun getSSBName(context: Context): String? {
        return getPrefs(context).getString(KEY_SSB_NAME, null)
    }

    fun clearSSBDate(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    fun getDaysRemaining(timestamp: Long): Int {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val diff = timestamp - today
        return if (diff < 0) -1 else TimeUnit.MILLISECONDS.toDays(diff).toInt()
    }
}