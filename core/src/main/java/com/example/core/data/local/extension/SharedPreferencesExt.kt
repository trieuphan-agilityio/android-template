package com.example.core.data.local.extension

import android.content.SharedPreferences
import androidx.core.content.edit

private const val CURR_USER_AUTH_USER_ID = "CURR_USER_AUTH_USER_ID"

/**
 * Removes a value with the given key.
 *
 * @param key Key of the value to be removed.
 */
fun SharedPreferences.remove(key: String) {
    this.edit() { this.remove(key) }
}

/**
 * Removes all keys and values.
 */
fun SharedPreferences.clear() {
    this.edit { this.clear() }
}

fun SharedPreferences.cacheUserAuthUser(userId: String) {
    this.edit { putString(CURR_USER_AUTH_USER_ID, userId) }
}