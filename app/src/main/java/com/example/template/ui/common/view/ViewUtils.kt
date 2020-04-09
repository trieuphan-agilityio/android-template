package com.example.template.ui.common.view

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.template.R
import kotlin.math.roundToInt

/**
 * Set listen for check box to show password input into edit text
 *
 * @param checkBox The check box listen event show/hide password
 * @param editText The edit text input password
 */
fun setListenShowPassword(checkBox: CheckBox, editText: EditText) {
    checkBox.setOnCheckedChangeListener { _, isChecked ->
        editText.transformationMethod = if (isChecked)
            PasswordTransformationMethod.getInstance()
        else
            HideReturnsTransformationMethod.getInstance()
    }
}

/**
 * Check the password is valid or not
 * At least 10 characters and no space.
 *
 * @param password - The {@link String} password
 * @return True if the password format is valid. Otherwise, return false.
 */
fun isValidPasswordFormat(password: String): Boolean {
    val pattern = "(?=\\S+$).{10,}"
    return password.matches(pattern.toRegex())
}


/**
 * convert dp to pixel
 *
 * @param context the context
 * @param dp      value in dp unit
 * @return value in pixel
 */
fun dpToPx(context: Context, dp: Int): Int {
    val displayMetrics = context.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

/**
 * Show mask text by length
 *
 * @param length Length of text
 * @param char Character for mask
 */
fun TextView.showMaskText(length: Int = 0, char: String = "•") {
    return setText(char.repeat(length))
}