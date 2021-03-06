package com.arny.pik.data.source

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.arny.pik.utils.Prefs
import com.arny.pik.utils.Utility

interface BaseRepository {
    fun getContext(): Context

    fun getPrefString(key: String, default: String? = null): String? {
        return Prefs.getInstance(getContext()).get(key) ?: default
    }

    fun getPrefInt(key: String, default: Int = 0): Int {
        return Prefs.getInstance(getContext()).get<Int>(key) ?: default
    }

    fun getPrefLong(key: String, default: Long = 0L): Long {
        return Prefs.getInstance(getContext()).get<Long>(key) ?: default
    }

    fun setPrefBoolean(key: String, value: Boolean) {
        Prefs.getInstance(getContext()).put(key, value)
    }

    fun setPrefInt(key: String, value: Int) {
        Prefs.getInstance(getContext()).put(key, value)
    }

    fun setPrefLong(key: String, value: Long) {
        Prefs.getInstance(getContext()).put(key, value)
    }

    fun getPrefBoolean(key: String, default: Boolean): Boolean {
        return Prefs.getInstance(getContext()).get<Boolean>(key) ?: false
    }

    fun isDebugApi(): Boolean {
        return Prefs.getInstance(getContext()).get<Boolean>("debug_api_requests") ?: false
    }

    fun removePref(vararg key: String) {
        Prefs.getInstance(getContext()).remove(*key)
    }

    fun setPrefString(key: String?, value: String?) {
        Prefs.getInstance(getContext()).put(key, value)
    }

    fun isConnected(): Boolean {
        return Utility.isConnected(getContext())
    }

    fun getString(res: Int): String {
        return getContext().getString(res) ?: ""
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(getContext(), id)
    }
}