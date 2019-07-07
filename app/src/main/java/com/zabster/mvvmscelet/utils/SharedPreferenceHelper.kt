package com.zabster.mvvmscelet.utils

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREF_NAME = "com.zabster.mvvmscelet.local_storage"

class SharedPreferenceHelper(applicationContext: Context) {

    private val sharedPref: SharedPreferences = applicationContext
        .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)


    fun putString(key: String, value: String?, defaultValue: String = "") {
        val editor = sharedPref.edit()
        editor.putString(key, value ?: defaultValue)
        editor.apply()
    }

    fun getString(key: String): String = sharedPref.getString(key, "") ?: ""

    fun putBoolean(key: String, value: Boolean?, defaultValue: Boolean = false) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value ?: defaultValue)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean = sharedPref.getBoolean(key, false)

    fun clearAll() {
        sharedPref.edit().clear().apply()
    }
}