package com.example.common.extensions

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Removes a value with the given key.
 *
 * @param key Key of the value to be removed.
 */
fun SharedPreferences.remove(key: String) {
    this.edit { this.remove(key) }
}

/**
 * Removes all keys and values.
 */
fun SharedPreferences.clear() {
    this.edit { this.clear() }
}