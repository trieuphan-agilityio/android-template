package com.example.common.utils

import androidx.core.util.PatternsCompat

open class InputValidator {
    companion object {

        fun email(input: String?): Boolean {
            if (input.isNullOrEmpty()) {
                return false
            }

            return PatternsCompat.EMAIL_ADDRESS.matcher(input).matches()
        }

        fun password(input: String?): Boolean {
            if (input.isNullOrEmpty()) {
                return false
            }

            return input.matches("(?=\\S+$).{10,}".toRegex())
        }

        fun username(input: String?): Boolean {
            // TODO: Implement rule later
            return !input.isNullOrEmpty()
        }
    }
}