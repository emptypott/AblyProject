package com.example.ablyproject.util

import android.content.Context
import android.content.SharedPreferences
import com.example.ablyproject.presentation.App.Companion.prefs

class PreferenceUtil(context: Context) {
    val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setBoolean(key : String, boolean : Boolean) {
        prefs.edit().putBoolean(key, boolean).apply()
    }
}

