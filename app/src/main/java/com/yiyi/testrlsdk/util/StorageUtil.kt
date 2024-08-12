package com.yiyi.testrlsdk.util

import android.content.Context
import android.content.SharedPreferences

class StorageUtil(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

    fun save(key: String, value: Any) {
        val editor = sharedPreferences.edit()

        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
            else -> {
                editor.putString(key, value.toString())
            }
        }
        editor.apply()
    }

    fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> {
                sharedPreferences.getString(key, defaultValue) as T
            }
            is Int -> {
                sharedPreferences.getInt(key, defaultValue) as T
            }
            is Boolean -> {
                sharedPreferences.getBoolean(key, defaultValue) as T
            }
            is Float -> {
                sharedPreferences.getFloat(key, defaultValue) as T
            }
            is Long -> {
                sharedPreferences.getLong(key, defaultValue) as T
            }
            else -> {
                defaultValue
            }
        }
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}